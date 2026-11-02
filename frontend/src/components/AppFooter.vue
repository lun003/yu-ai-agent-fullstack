<template>
  <footer class="app-footer">
    <!-- 链接区 -->
    <nav class="footer-links">
      <a
        v-for="link in links"
        :key="link.label"
        :href="link.url"
        target="_blank"
        rel="noopener noreferrer"
        class="footer-link"
      >{{ link.label }}</a>
    </nav>

    <!-- 版权区 -->
    <div class="footer-copy">
      <p class="copyright">
        Copyright © {{ yearRange }} {{ siteName }} · All Rights Reserved
      </p>
      <p class="icp">
        <a href="https://beian.miit.gov.cn/" target="_blank" rel="noopener noreferrer">
          沪ICP备 2021000000 号-1
        </a>
        <span class="dot">·</span>
        <span>沪公网安备 31011502000000 号</span>
      </p>
    </div>
  </footer>
</template>

<script setup>
const siteName = '鱼 AI 应用平台'
// 起始年 ~ 当前年，自动滚动
const startYear = 2021
const yearRange = `${startYear}-${new Date().getFullYear()}`

// 底部相关链接
const links = [
  { label: '编程导航', url: 'https://www.codefather.cn' },
  { label: '面试鸭', url: 'https://www.mianshiya.com' },
  { label: '用户协议', url: '#' },
  { label: '隐私政策', url: '#' }
]
</script>

<style scoped>
.app-footer {
  margin-top: auto;
  padding: 32px 24px 28px;
  text-align: center;
  background: transparent;
  border-top: 1px solid var(--border);
}

.footer-links {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 8px;
}

.footer-link {
  position: relative;
  color: var(--text-2);
  text-decoration: none;
  font-size: 13px;
  padding: 0 12px;
  transition: color 0.2s var(--ease);
}

/* 相邻链接之间的细分隔线 */
.footer-link + .footer-link::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 1px;
  height: 12px;
  background: var(--border-strong);
}

.footer-link:hover {
  color: var(--accent);
}

.footer-copy {
  margin-top: 14px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.copyright,
.icp {
  font-size: 12.5px;
  color: var(--text-3);
  letter-spacing: 0.2px;
}

.icp a {
  color: var(--text-3);
  text-decoration: none;
  transition: color 0.2s var(--ease);
}

.icp a:hover {
  color: var(--accent);
}

.icp .dot {
  margin: 0 8px;
}

@media (max-width: 480px) {
  .app-footer {
    padding: 26px 16px 22px;
  }
  .footer-link {
    padding: 0 9px;
  }
}
</style>
