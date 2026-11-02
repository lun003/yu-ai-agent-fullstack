<template>
  <div class="message" :class="role">
    <!-- AI 头像在左：优先展示默认头像图片，无图片时回退表情 -->
    <div v-if="role === 'ai'" class="avatar ai-avatar">
      <img v-if="aiAvatar" :src="aiAvatar" alt="AI" class="avatar-img" />
      <span v-else>{{ aiEmoji }}</span>
    </div>
    <!-- 消息气泡 -->
    <div class="bubble">
      <span class="content">{{ content }}</span>
      <span v-if="pending" class="typing">…</span>
    </div>
    <!-- 用户头像在右 -->
    <div v-if="role === 'user'" class="avatar user-avatar">🧑</div>
  </div>
</template>

<script setup>
defineProps({
  // 'user' | 'ai'
  role: {
    type: String,
    required: true
  },
  // 消息内容
  content: {
    type: String,
    default: ''
  },
  // AI 回复流式未结束时显示打字指示
  pending: {
    type: Boolean,
    default: false
  },
  // AI 头像表情（无头像图片时回退使用）
  aiEmoji: {
    type: String,
    default: '🤖'
  },
  // AI 默认头像图片地址（data URI 或 URL）
  aiAvatar: {
    type: String,
    default: ''
  }
})
</script>

<style scoped>
.message {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  margin-bottom: 22px;
}

/* 用户消息靠右 */
.message.user {
  justify-content: flex-end;
}

.avatar {
  flex-shrink: 0;
  width: 38px;
  height: 38px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  background: var(--surface);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-xs);
  overflow: hidden;
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.user-avatar {
  background: var(--surface-2);
}

.bubble {
  max-width: 72%;
  padding: 12px 16px;
  border-radius: var(--radius-lg);
  line-height: 1.65;
  font-size: 14.5px;
  /* 保留后端流式内容的换行符 */
  white-space: pre-wrap;
  word-break: break-word;
  background: var(--surface);
  color: var(--text);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-xs);
}

/* AI 回复文字统一左对齐 */
.message.ai .bubble {
  text-align: left;
  border-top-left-radius: 6px;
}

.message.user .bubble {
  background: var(--accent, #4dabf7);
  color: #fff;
  text-align: left;
  border-color: transparent;
  border-top-right-radius: 6px;
  box-shadow: var(--shadow-sm);
}

.typing {
  animation: blink 1.2s infinite;
  color: var(--text-3);
}

@keyframes blink {
  0%,
  100% {
    opacity: 0.25;
  }
  50% {
    opacity: 1;
  }
}

/* 平板 */
@media (max-width: 768px) {
  .bubble {
    max-width: 78%;
  }
}

/* 手机 */
@media (max-width: 480px) {
  .message {
    gap: 9px;
    margin-bottom: 16px;
  }
  .avatar {
    width: 32px;
    height: 32px;
    font-size: 17px;
  }
  .bubble {
    max-width: 80%;
    padding: 10px 13px;
    font-size: 14px;
  }
}
</style>
