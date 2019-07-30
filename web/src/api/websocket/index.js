import SockJs from 'sockjs-client'
import Stomp from 'stompjs'
import Config from '@/config'
import { Notification } from 'element-ui'

export function initWebSocket () {
  connection()
  this.timer = setInterval(() => {
    try {
      this.stompClient.send('test')
    } catch (err) {
      console.log('Socket掉线了：' + err)
      this.connection()
    }
  }, 30000)
}

/**
 * @description 建立连接
 */
function connection () {
  this.socket = new SockJs(Config.webSocketUrl)
  this.stompClient = Stomp.over(this.socket)
  this.stompClient.connect({}, frame => {
    Notification.success({
      message: 'WebSocket连接成功！'
    })
    this.stompClient.subscribe(
      Config.subscribeTopic,
      res => {
        console.log(res)
      },
      err => {
        console.log('出错啦：' + err)
        Notification.error({
          message: 'WebSocket连接失败！'
        })
      }
    )
  })
}

/**
 * @description 关闭连接
 */
export function disConnect () {
  if (this.stompClient != null) {
    this.stompClient.disconnect()
  }
  console.log('连接已关闭！')
}
