local config = {}
lux = require("lux")
config.hostname = "Node-MCU"
config.WifiParam = {}
config.WifiParam.ssid = "aabbcc"
config.WifiParam.pwd = "ddccbbaa"
config.WifiParam.save = true

ProductKey = ""
DeviceName = ""
DeviceSecret = ""
ClientId = wifi.sta.getmac()
RegionId = ""

MyMqttPort = 1883
MyMqtt = nil

MyMqttHost = ProductKey..".iot-as-mqtt."..RegionId..".aliyuncs.com"
MyMqttUsername = DeviceName.."&"..ProductKey

topic0="/"..ProductKey.."/"..DeviceName.."/user/node"
updateCloud = "/sys/"..ProductKey.."/"..DeviceName.."/thing/event/property/post"
getCloud = "/"..ProductKey.."/"..DeviceName.."/user/get"

time = 0

tmr.delay(50000)

wifi.sta.clearconfig()
wifi.setmode(wifi.STATION)
wifi.sta.sethostname(config.hostname)
wifi.sta.config(config.WifiParam)
wifi.sta.autoconnect(1)
wifi.sta.connect()

wifi.eventmon.register(wifi.eventmon.STA_GOT_IP, function(T)
     print("Connected, IP is "..wifi.sta.getip())
end)

wifi.eventmon.register(wifi.eventmon.STA_DISCONNECTED, function(T)
     print("wifi disconnect")
end)

function GetNetTime()
    sntp.sync({"ntp1.aliyun.com","ntp2.aliyun.com","ntp3.aliyun.com","ntp4.aliyun.com","ntp5.aliyun.com","ntp6.aliyun.com","ntp7.aliyun.com"},
         function(sec, usec, server, info)
             print('sync:', sec, usec, server)
             time = string.format(sec.."000")
         end,
         function()
            print("get time error")
         end)
    return time
end

myMQTTtimes='6666'
hmacdata="clientId"..ClientId.."deviceName"..DeviceName.."productKey"..ProductKey.."timestamp"..myMQTTtimes
myMQTTpassword=crypto.toHex(crypto.hmac("sha1", hmacdata, DeviceSecret))
myMQTTClientId=ClientId.."|securemode=3,signmethod=hmacsha1,timestamp="..myMQTTtimes.."|"
myMQTT=mqtt.Client(myMQTTClientId, 120, MyMqttUsername, myMQTTpassword)

myTimer = tmr.create()
MQTTconnectFlag=0
myTimer:alarm(1000 * 10, tmr.ALARM_AUTO, function()
    if myMQTT ~= nil then
        print("Attempting client connect...")
        myMQTT:connect(MyMqttHost, MyMqttPort, 0, MQTTSuccess, MQTTFailed)
    end
end)

function MQTTSuccess(client)
    print("MQTT connected")
    client:subscribe(getCloud, 0, function(conn)
        print("subscribe success")
    end)
    myMQTT=client
    MQTTconnectFlag=1
    myTimer:stop()
end

function MQTTFailed(client,reson)
    print("Fail reson:"..reson)
    MQTTconnectFlag=0
    myTimer:start()
end

myMQTT:on("offline", function(client)
    print ("offline")
    myTimer:start()
end)

myMQTT:on("message", function(client, topic, data)
    if data ~= nil then
        weather = {}
        print(data)
        demo = sjson.decode(data)
        openLed(demo)
    end
end)

function openLed(demo)
    if demo.params.on == 0 then
        gpio.write(switch, gpio.HIGH)
   elseif demo.params.on == 1 then
       gpio.write(switch, gpio.LOW)
    end
end

switch = 1
pin = 2
mhumi = 0
mtemp = 0
on = 0
gpio.mode(switch, gpio.OUTPUT)
gpio.write(switch, gpio.HIGH)
local function getData()
    status, temp, humi, temp_dec, humi_dec = dht.read11(pin)
    if status == dht.OK then
        mhumi = humi
        mtemp = temp
    elseif status == dht.ERROR_CHECKSUM then
        print( "DHT Checksum error." )
        mhumi = 0
        mtemp = 0
    elseif status == dht.ERROR_TIMEOUT then
        print( "DHT timed out." )
        mhumi = 0
        mtemp = 0
    end

    on = 0 
    if gpio.read(switch) == 0 then
        on = 1
    elseif gpio.read(switch) == 1 then
        on = 0
    end

end

local function subParam()
    data = string.format([[{
  "id": "123",
  "version": "1.0",
  "params": {
    "mhumi": {
      "value": %d,
      "time": %s
    },
    "mtemp": {
      "value": %d,
      "time": %s
    },
    "on": {
      "value": %d,
      "time": %s
    },
    "mlux": {
      "value": %d,
      "time": %s
    }
  },
  "method": "thing.event.property.post"
}]], mhumi, time, mtemp, time, on, time, lux.Lux, time)
    return data
end

UploadTimer = tmr.create()
UploadTimer:alarm(3000, tmr.ALARM_AUTO, function()
    if MQTTconnectFlag==1 and myMQTT~=nil then
        lux.getLux()
        GetNetTime()
        getData()
        myMQTT:publish(updateCloud, subParam(), 0, 0, function(client)
            print("send ok!\r\n"..subParam())
        end)
    end
end)

