#include <ArduinoJson.h>

#include <ESP8266MQTT.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#include <Wire.h>
#include "Protocentral_MAX30205.h"

/* 连接您的WIFI SSID和密码 */
#define WIFI_SSID "aabbcc"
#define WIFI_PASSWD "ddccbbaa"

#define PRODUCT_KEY ""
#define DEVICE_NAME ""
#define DEVICE_SECRET ""
#define REGION_ID ""
#define MQTT_SERVER ""
#define MQTT_PORT 1883
#define MQTT_USRNAME ""
#define CLIENT_ID ""
#define MQTT_PASSWD ""
#define ALINK_BODY_FORMAT "{\"id\":\"123\",\"version\":\"1.0\",\"method\":\"thing.event.property.post\",\"params\":%s}"
#define ALINK_TOPIC_PROP_POST "" // 发送通道
#define ALINK_TOPIC_PROP_SET "" // 订阅通道

int led = 13;
MAX30205 tempSensor;
int PulseSensorPurplePin = 17;
int LED13 = 2;
int Signal;
int Threshold = 600;

void onConnectionEstablished();

ESP8266MQTT client(
    WIFI_SSID,               // Wifi ssid
    WIFI_PASSWD,             // Wifi password
    MQTT_SERVER,             // MQTT broker ip
    MQTT_PORT,               // MQTT broker port
    MQTT_USRNAME,            // MQTT username
    MQTT_PASSWD,             // MQTT password
    CLIENT_ID,               // Client name
    onConnectionEstablished, // Connection established callback
    false,                   // Enable web updater
    true                     // Enable debug messages
);

// 登录成功后回调函数
void onConnectionEstablished()
{
  // 订阅通道
  client.subscribe(ALINK_TOPIC_PROP_SET, [](const String &payload) {
    parsingParams(payload);
  });
}

// 处理订阅的消息
void parsingParams(String params)
{
  const size_t capacity = JSON_OBJECT_SIZE(1) + JSON_OBJECT_SIZE(4) + 90;
  DynamicJsonDocument doc(capacity);
  deserializeJson(doc, params);

  const char *method = doc["method"];
  const char *id = doc["id"];
  int params_LightSwitch = doc["params"]["LightSwitch"];
  const char *version = doc["version"];

  if (params_LightSwitch == 1)
  {
    digitalWrite(led, HIGH);
  }
  else
  {
    digitalWrite(led, LOW);
  }
}

// 上报数据
void mqttSendMsg(float AnimalTemperature, int mhumi, int LightSwitch)
{
  char param[256];
  char jsonBuf[512];

  sprintf(param, "{\"AnimalTemperature\":{\"value\": %f},\"mhumi\":{\"value\": %d},\"LightSwitch\":{\"value\": %d}}", AnimalTemperature, mhumi, LightSwitch);
  sprintf(jsonBuf, ALINK_BODY_FORMAT, param);
  Serial.println(jsonBuf);
  client.publish(ALINK_TOPIC_PROP_POST, jsonBuf);
}

void setup()
{
  Serial.begin(115200);
  pinMode(led, OUTPUT);
  pinMode(LED13, OUTPUT);
  Wire.begin();
  tempSensor.begin();
}

void loop()
{
  client.loop();

  int Signal = analogRead(PulseSensorPurplePin);
  if (Signal > Threshold)
  {
    digitalWrite(LED13, HIGH);
  }
  else
  {
    digitalWrite(LED13, LOW);
  }

  double temp = tempSensor.getTemperature();

  int LightSwitch = digitalRead(led);

  mqttSendMsg(temp, Signal, LightSwitch);

  delay(1000);
}
