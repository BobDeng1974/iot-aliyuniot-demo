export default {
  /**
   * @description 是否显示logo
   */
  sidebarLogo: true,

  /**
   * @description websocket服务端地址
   */
  webSocketUrl: 'https://api.iot.thunk.cn/socket',
  // webSocketUrl: 'http://127.0.0.1:2500/socket',

  /**
   * @description 广播Topic
   * b3310mBFdTyYNZTdXAJL000100
   * b3310mBFdTyYNZTdXAJL000100
   * "vbRnWfpZtlEigeCMAVSV000100"
   */
  nodeMcuTopic: '/topic/data/b3310mBFdTyYNZTdXAJL000100',
  wemosTopic: '/topic/data/vbRnWfpZtlEigeCMAVSV000100',
  systemInfoTopic: '/topic/system'
}
