/**
 * 生成 AI 默认头像（自包含 SVG data URI，无需任何外部图片资源）
 * 以应用主题色的柔和渐变圆形 + emoji 组成，保证每个应用都有独立的默认头像
 * @param {string} emoji 应用标识表情
 * @param {string} color 主题色
 * @returns {string} 可直接用于 <img src> 的 data URI
 */
export function emojiAvatar(emoji = '🤖', color = '#4dabf7') {
  const svg = `<svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" viewBox="0 0 80 80">
    <defs>
      <linearGradient id="bg" x1="0" y1="0" x2="1" y2="1">
        <stop offset="0%" stop-color="${color}" stop-opacity="0.18"/>
        <stop offset="100%" stop-color="${color}" stop-opacity="0.06"/>
      </linearGradient>
    </defs>
    <rect width="80" height="80" rx="24" fill="url(#bg)"/>
    <text x="40" y="46" font-size="38" text-anchor="middle" dominant-baseline="middle">${emoji}</text>
  </svg>`
  return `data:image/svg+xml;charset=utf-8,${encodeURIComponent(svg)}`
}
