module = {}
module.Lux = 0

i2c.setup(0, 5, 6, i2c.SLOW)

local function readGY30(mode)
     i2c.start(0)
     i2c.address(0, 0x23, i2c.TRANSMITTER)
     i2c.write(0, 0x01)
     i2c.stop(0)
     i2c.start(0)
     i2c.address(0, 0x23, i2c.TRANSMITTER)
     i2c.write(0, mode)
     i2c.stop(0)
     
     tmr.create():alarm(129,tmr.ALARM_SINGLE,function()
       i2c.start(0)
       i2c.address(0, 0x23, i2c.RECEIVER)
       local c = i2c.read(0, 2)
       i2c.stop(0)
       if string.byte(c,1)~=0 then
           local lx=string.byte(c,1)*256+string.byte(c,2)
           module.Lux = lx
       else
           local lx=string.byte(c,2)
           module.Lux = lx
       end
     end)
end

function module.getLux()
    readGY30(0x20)
end

return module
