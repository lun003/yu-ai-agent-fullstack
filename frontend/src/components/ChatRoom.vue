<template>
  <div class="chat-page" :style="{ '--accent': accent }">
    <!-- 顶部栏 -->
    <header class="chat-header">
      <router-link to="/" class="back-btn">← 返回</router-link>
      <h1 class="title">{{ aiEmoji }} {{ title }}</h1>
      <span class="session-info" v-if="sessionTip">{{ sessionTip }}</span>
    </header>

    <!-- 聊天记录区 -->
    <div ref="listRef" class="chat-list">
      <div class="chat-list-inner">
        <!-- 欢迎语 -->
        <ChatMessage
          v-if="welcome"
          role="ai"
          :content="welcome"
          :ai-emoji="aiEmoji"
          :ai-avatar="resolvedAiAvatar"
        />
        <ChatMessage
          v-for="(msg, index) in messages"
          :key="index"
          :role="msg.role"
          :content="msg.content"
          :ai-emoji="aiEmoji"
          :ai-avatar="resolvedAiAvatar"
          :pending="loading && index === messages.length - 1 && msg.role === 'ai' && !msg.content"
        />
      </div>
    </div>

    <!-- 底部输入区 -->
    <footer class="chat-input-bar">
      <div class="input-shell">
        <input
          v-model="inputText"
          class="chat-input"
          type="text"
          :placeholder="placeholder"
          :disabled="loading"
          @keyup.enter="send"
        />
        <button class="send-btn" :disabled="loading || !inputText.trim()" @click="send">
          {{ loading ? '回答中' : '发送' }}
        </button>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed, nextTick, ref, watch, onBeforeUnmount } from 'vue'
import ChatMessage from './ChatMessage.vue'
import { emojiAvatar } from '../utils/avatar'

const props = defineProps({
  // 页面标题
  title: {
    type: String,
    required: true
  },
  // AI 头像表情
  aiEmoji: {
    type: String,
    default: '🤖'
  },
  // AI 默认头像图片（不传则根据表情 + 主题色自动生成）
  aiAvatar: {
    type: String,
    default: ''
  },
  // 主题色（用户气泡 / 发送按钮 / 强调点）
  accent: {
    type: String,
    default: '#4dabf7'
  },
  // 欢迎语
  welcome: {
    type: String,
    default: ''
  },
  // 顶部会话提示（如会话 ID）
  sessionTip: {
    type: String,
    default: ''
  },
  // 输入框占位文案
  placeholder: {
    type: String,
    default: '请输入内容…'
  },
  // 每收到一条 SSE 消息是否额外追加一个换行
  // （超级智能体后端每完成一步才响应一次，逐条换行更清晰）
  appendNewlinePerChunk: {
    type: Boolean,
    default: false
  },
  // 必填：根据用户输入构建 SSE 接口完整地址
  buildUrl: {
    type: Function,
    required: true
  }
})

// 实际使用的 AI 默认头像：外部优先，否则按应用表情 + 主题色生成
const resolvedAiAvatar = computed(
  () => props.aiAvatar || emojiAvatar(props.aiEmoji, props.accent)
)

const messages = ref([])
const inputText = ref('')
const loading = ref(false)
const listRef = ref(null)
let eventSource = null

// 消息更新后自动滚动到底部
watch(
  messages,
  () => {
    nextTick(() => {
      if (listRef.value) {
        listRef.value.scrollTop = listRef.value.scrollHeight
      }
    })
  },
  { deep: true }
)

// SSE 数据中换行符可能被转义，这里还原以便阅读
function normalizeData(data) {
  return data.replace(/\\n/g, '\n')
}

function send() {
  const text = inputText.value.trim()
  if (!text || loading.value) {
    return
  }
  // 追加用户消息
  messages.value.push({ role: 'user', content: text })
  // 预留一条 AI 消息，流式追加内容
  const aiIndex = messages.value.length
  messages.value.push({ role: 'ai', content: '' })
  inputText.value = ''
  loading.value = true

  // 通过 EventSource 建立 SSE 长连接，实时接收对话内容
  eventSource = new EventSource(props.buildUrl(text))

  eventSource.onmessage = (event) => {
    let chunk = normalizeData(event.data)
    // 超级智能体：每完成一步后端才响应一次，逐条消息后补一个换行分隔
    if (props.appendNewlinePerChunk) {
      chunk += '\n'
    }
    // 必须通过响应式数组下标修改，才能触发视图更新
    messages.value[aiIndex].content += chunk
  }

  eventSource.onerror = () => {
    // 流结束或连接异常时关闭连接，避免自动重连导致重复问答
    closeEventSource()
    if (!messages.value[aiIndex].content) {
      messages.value[aiIndex].content = '（未能获取到回复，请检查后端服务是否启动）'
    }
  }
}

function closeEventSource() {
  if (eventSource) {
    eventSource.close()
    eventSource = null
  }
  loading.value = false
}

// 离开页面时释放连接
onBeforeUnmount(closeEventSource)
</script>

<style scoped>
.chat-page {
  height: 100%;
  display: flex;
  flex-direction: column;
  max-width: 860px;
  margin: 0 auto;
  background: var(--bg);
}

/* 顶部栏：主题色实色背景（保留原样式） */
.chat-header {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 12px;
  padding: 14px 16px;
  background: var(--accent);
  color: #fff;
}

.back-btn {
  color: #fff;
  text-decoration: none;
  font-size: 14px;
  opacity: 0.9;
}

.back-btn:hover {
  opacity: 1;
}

.title {
  font-size: 18px;
  font-weight: 600;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.session-info {
  font-size: 12px;
  opacity: 0.85;
}

.chat-list {
  flex: 1;
  overflow-y: auto;
  padding: 26px 20px;
}

.chat-list-inner {
  display: flex;
  flex-direction: column;
}

/* 悬浮胶囊输入区 */
.chat-input-bar {
  padding: 14px 20px 22px;
  background: transparent;
}

.input-shell {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 8px 8px 18px;
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 999px;
  box-shadow: var(--shadow-sm);
  transition: border-color 0.2s var(--ease), box-shadow 0.2s var(--ease);
}

.input-shell:focus-within {
  border-color: var(--accent);
  box-shadow: var(--shadow-md);
}

.chat-input {
  flex: 1;
  min-width: 0;
  border: none;
  outline: none;
  background: transparent;
  font-size: 15px;
  color: var(--text);
}

.chat-input::placeholder {
  color: var(--text-3);
}

.send-btn {
  flex-shrink: 0;
  padding: 10px 22px;
  border: none;
  border-radius: 999px;
  background: var(--accent);
  color: #fff;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: filter 0.2s var(--ease), opacity 0.2s var(--ease);
}

.send-btn:hover:not(:disabled) {
  filter: brightness(1.05);
}

.send-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* 手机：聊天室占满全屏，压缩间距 */
@media (max-width: 480px) {
  .chat-page {
    max-width: 100%;
  }
  .chat-header {
    padding: 12px 14px;
  }
  .session-info {
    display: none;
  }
  .chat-list {
    padding: 18px 14px;
  }
  .chat-input-bar {
    padding: 10px 14px 16px;
  }
  .send-btn {
    padding: 10px 18px;
  }
}
</style>
