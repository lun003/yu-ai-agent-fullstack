<template>
  <ChatRoom
    title="AI 超级智能体"
    ai-emoji="🤖"
    accent="#4dabf7"
    welcome="我是 AI 超级智能体，可以联网搜索、网页抓取、执行命令、读写文件、生成 PDF。给我一个任务吧，例如：帮我搜索最新的 AI 新闻并生成一份 PDF 报告。"
    placeholder="给智能体下达一个任务…"
    append-newline-per-chunk
    :build-url="buildUrl"
  />
</template>

<script setup>
import ChatRoom from '../components/ChatRoom.vue'
import { API_BASE_URL } from '../config'

/**
 * 构建 SSE 接口地址
 * 对应后端 AiController#doChatWithManus
 * GET /api/ai/manus/chat?message=xxx
 */
function buildUrl(message) {
  const params = new URLSearchParams({ message })
  return `${API_BASE_URL}/ai/manus/chat?${params.toString()}`
}
</script>
