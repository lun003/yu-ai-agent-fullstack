<template>
  <router-view />
</template>

<script setup>
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 现代极简设计令牌：中性色阶 + 细边框 + 分层柔和阴影 */
:root {
  --bg: #f6f7f9;
  --surface: #ffffff;
  --surface-2: #f3f4f6;
  --text: #1f2328;
  --text-2: #6b7280;
  --text-3: #9aa1ab;
  --border: #e9ebef;
  --border-strong: #dfe2e8;
  --accent: #4dabf7;
  --radius-xl: 22px;
  --radius-lg: 16px;
  --radius-md: 12px;
  --radius-sm: 10px;
  --shadow-xs: 0 1px 2px rgba(16, 24, 40, 0.04);
  --shadow-sm: 0 2px 10px rgba(16, 24, 40, 0.05);
  --shadow-md: 0 12px 32px rgba(16, 24, 40, 0.08);
  --ease: cubic-bezier(0.22, 1, 0.36, 1);
}

html,
body,
#app {
  height: 100%;
}

body {
  font-family: 'PingFang SC', 'Microsoft YaHei', 'Segoe UI', -apple-system,
    BlinkMacSystemFont, Arial, sans-serif;
  background-color: var(--bg);
  color: var(--text);
  line-height: 1.5;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

/* 极简细滚动条 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.14);
  border-radius: 8px;
}
::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.22);
}
</style>
