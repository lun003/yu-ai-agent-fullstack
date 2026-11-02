<template>
  <div class="home-page">
    <main class="home-main">
      <div class="home-inner">
        <p class="eyebrow">AI APPLICATIONS</p>
        <h1 class="logo">🐟 鱼 AI 应用平台</h1>
        <p class="sub-title">选择一个 AI 应用，开始你的智能对话之旅</p>

        <div class="app-cards">
          <router-link
            v-for="app in apps"
            :key="app.route"
            :to="app.route"
            class="app-card"
            :style="{ '--card-accent': app.accent }"
          >
            <div class="app-icon">{{ app.icon }}</div>
            <h2 class="app-name">{{ app.name }}</h2>
            <p class="app-desc">{{ app.desc }}</p>
            <span class="app-enter">进入应用 <span class="arrow">→</span></span>
          </router-link>
        </div>
      </div>
    </main>

    <!-- 网站底部版权信息 -->
    <AppFooter />
  </div>
</template>

<script setup>
import AppFooter from '../components/AppFooter.vue'

// 主页应用列表
const apps = [
  {
    name: 'AI 恋爱大师',
    icon: '💕',
    desc: '脱单分析、约会规划、恋爱答疑，你的专属 AI 恋爱军师',
    route: '/love_app',
    accent: '#f06595'
  },
  {
    name: 'AI 超级智能体',
    icon: '🤖',
    desc: '基于 ReAct 推理的多工具智能体，搜索、爬取、写文件、生成 PDF 样样精通',
    route: '/manus',
    accent: '#4dabf7'
  }
]
</script>

<style scoped>
.home-page {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  background:
    radial-gradient(1200px 600px at 15% -10%, rgba(77, 171, 247, 0.08), transparent 60%),
    radial-gradient(1000px 500px at 100% 0%, rgba(240, 101, 149, 0.07), transparent 55%),
    var(--bg);
}

.home-main {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 64px 24px;
}

.home-inner {
  width: 100%;
  max-width: 880px;
  text-align: center;
}

.eyebrow {
  font-size: 12px;
  letter-spacing: 0.28em;
  color: var(--text-3);
  font-weight: 600;
}

.logo {
  margin-top: 14px;
  font-size: 38px;
  font-weight: 700;
  letter-spacing: -0.5px;
  color: var(--text);
}

.sub-title {
  margin-top: 12px;
  color: var(--text-2);
  font-size: 15px;
}

.app-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 22px;
  margin-top: 52px;
}

.app-card {
  position: relative;
  /* 默认强调色，运行时由内联 :style 覆盖 */
  --card-accent: var(--accent);
  text-align: left;
  padding: 30px 28px;
  border-radius: var(--radius-xl);
  background: var(--surface);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-sm);
  text-decoration: none;
  color: var(--text);
  transition: transform 0.3s var(--ease), box-shadow 0.3s var(--ease),
    border-color 0.3s var(--ease);
  overflow: hidden;
}

.app-card::after {
  content: '';
  position: absolute;
  inset: 0;
  border-radius: inherit;
  background: linear-gradient(160deg, color-mix(in srgb, var(--card-accent) 8%, transparent), transparent 45%);
  opacity: 0;
  transition: opacity 0.3s var(--ease);
  pointer-events: none;
}

.app-card:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-md);
  border-color: color-mix(in srgb, var(--card-accent) 35%, var(--border));
}

.app-card:hover::after {
  opacity: 1;
}

.app-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60px;
  height: 60px;
  font-size: 30px;
  border-radius: var(--radius-lg);
  background: color-mix(in srgb, var(--card-accent) 12%, var(--surface-2));
}

.app-name {
  margin-top: 20px;
  font-size: 19px;
  font-weight: 600;
  color: var(--text);
}

.app-desc {
  margin-top: 10px;
  font-size: 13.5px;
  color: var(--text-2);
  line-height: 1.7;
  min-height: 46px;
}

.app-enter {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  margin-top: 20px;
  font-size: 14px;
  color: var(--card-accent);
  font-weight: 600;
}

.app-enter .arrow {
  transition: transform 0.3s var(--ease);
}

.app-card:hover .app-enter .arrow {
  transform: translateX(4px);
}

/* 平板 */
@media (max-width: 768px) {
  .home-main {
    padding: 48px 20px;
  }
  .logo {
    font-size: 30px;
  }
  .app-cards {
    gap: 16px;
    margin-top: 40px;
  }
}

/* 手机：卡片纵向堆叠 */
@media (max-width: 480px) {
  .home-main {
    padding: 40px 16px;
  }
  .logo {
    font-size: 26px;
  }
  .sub-title {
    font-size: 14px;
  }
  .app-cards {
    grid-template-columns: 1fr;
    margin-top: 32px;
  }
  .app-desc {
    min-height: 0;
  }
}
</style>
