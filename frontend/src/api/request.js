import axios from 'axios'
import { API_BASE_URL } from '../config'

/**
 * Axios 实例：用于普通（非流式）HTTP 请求
 * SSE 流式接口由 EventSource 直接建立长连接
 */
const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 60000
})

// 统一响应处理
request.interceptors.response.use(
  (response) => response.data,
  (error) => {
    console.error('请求失败:', error)
    return Promise.reject(error)
  }
)

export default request
