<template>
  <ChatRoom
    title="AI 恋爱大师"
    ai-emoji="💕"
    accent="#f06595"
    welcome="你好呀～我是你的 AI 恋爱大师，关于脱单、约会、相处的问题都可以问我哦！"
    :session-tip="`会话 ID：${chatId}`"
    placeholder="问问恋爱大师，比如：怎么向喜欢的人表白？"
    :build-url="buildUrl"
  />
</template>

<script setup>
import { ref } from 'vue'
import ChatRoom from '../components/ChatRoom.vue'
import { API_BASE_URL } from '../config'
import { genUuid } from '../utils/uuid'

// 进入页面后自动生成聊天室 id，用于区分不同会话
const chatId = ref(genUuid())

/**
 * 构建 SSE 接口地址
 * 对应后端 AiController#doChatWithLoveAppSse
 * GET /api/ai/love_app/chat/sse?message=xxx&chatId=xxx
 */
function buildUrl(message) {
  const params = new URLSearchParams({
    message,
    chatId: chatId.value
  })
  return `${API_BASE_URL}/ai/love_app/chat/sse?${params.toString()}`
}
</script>
