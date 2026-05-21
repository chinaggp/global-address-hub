# GlobalAddressHub 🌍 (多国随机地址生成器 / SEO工具站)

> **当前版本**：v1.0.0-MVP  
> **核心宗旨**：走最轻量的路，做最狂野的 SEO 流量主！

---

## 🚨 极其重要的状态声明 (Developer Notice)

**注意了兄弟们！目前的后端（`backend` 目录）已经暂时被“打入冷宫”废弃了！** ❄️  
为了以最快的速度上线验证 MVP（最小可行性产品）、疯狂蹭 Google SEO 的自然流量，同时省去买服务器、配数据库、维护 API 接口的精力和钱包开销，**我们把所有的地址生成逻辑、规则引擎以及基础数据，全部移植到了前端 `frontend` 中**！ 

也就是说：
- **现在项目是“纯前端、零后端、静态化/Serverless 运行”的！**
- 后续如果项目发展得牛逼了，需要做用户系统、VIP 充值、API Key 售卖等高级割韭菜操作时，我们才会从“冷宫”里捞出 `backend` (Spring Boot 3) 重新开发。目前请尽情忽视它，或者只把它当成技术储备遗产。

---

## 🏗️ 整体架构演变

### 过去的设计 (前后端分离)：
```text
用户浏览器 ➔ Cloudflare CDN ➔ Nuxt 3 前端 ➔ Spring Boot 后端 ➔ 规则引擎/数据文件
```

### 现在的超轻量设计 (全前端离线运行)：
```text
用户浏览器 ➔ Cloudflare Pages / CDN (Nuxt 3 静态生成 SSR / SSG) 
                 └─► [前端内置 Rule Engine] ◄──► [前端本地 JSON 模板数据]
```
所有地址生成的随机算法都在用户浏览器（或 SSG 阶段）直接完成，请求响应速度高达 **0ms**，性能直接起飞！🚀

---

## 🎨 前端技术方案 (`frontend`)

前端基于 **Nuxt 3** 构建，完美适配 SEO 工具站的性能和搜索引擎收录需求。

### 1. 技术栈大杂烩
*   **核心框架**：Nuxt 3 (Vue 3)
*   **开发语言**：TypeScript (强类型，告别 `any` 乱飞)
*   **样式方案**：TailwindCSS (原子化 CSS，让你写样式像在坐火箭)
*   **国际化**：`@nuxtjs/i18n` (多语言，默认英语 `en`，支持中文 `zh`)
*   **站点地图**：`@nuxtjs/sitemap` (自动生成 `sitemap.xml`，SEO 收录的生命线)

### 2. 怎么跑起来？(本地开发与打包)

首先确保你的电脑里有 Node.js（推荐 v18+ 或 v20+），然后切到 `frontend` 目录：

```bash
# 1. 进到前端目录
cd frontend

# 2. 装依赖 (推荐使用 npm，如果你喜欢 yarn/pnpm 也可以)
npm install

# 3. 跑起本地开发服务器 (支持热更新)
npm run dev

# 4. 静态生成所有国家的 SEO 独立页面 (输出到 .output/public 目录，可直接丢到 CF Pages 部署)
npm run generate

# 5. 本地预览打包出来的静态文件
npm run preview
```

### 3. 前端目录结构与核心业务逻辑
在前端中，所有的“业务逻辑”隐藏在以下三个关键文件夹中：

```text
frontend/
 ├─ data/
 │   ├─ country-pages.ts            # 各国的 SEO 独立配置 (Title, Description, Hero 文案, FAQ 结构化数据)
 │   └─ address/                    # 各国真实地址模板 JSON 库 (包含常用州、城市、邮编范围、姓氏、名字等)
 │       ├─ us-address.json         # 美国
 │       ├─ jp-address.json         # 日本
 │       ├─ uk-address.json         # 英国
 │       ├─ ca-address.json         # 加拿大
 │       ├─ au-address.json         # 澳大利亚
 │       ├─ tr-address.json         # 土耳其
 │       └─ ng-address.json         # 尼日利亚
 │
 ├─ composables/                    # 💡 核心业务逻辑实现 (规则引擎)
 │   ├─ useAddressGenerator.ts      # 【规则引擎核心】实现根据各国的行政区划、邮编格式、电话格式随机拼装出超逼真地址
 │   ├─ useCountryService.ts        # 国家与州/省列表的数据管理与检索服务
 │   ├─ useCountryContent.ts        # 配合 Nuxt3 获取页面对应国家的 SEO 标题和 FAQ 内容
 │   ├─ useCopyFormat.ts            # 处理地址在剪贴板复制时的格式转换 (支持单行、多行)
 │   └─ useAddressApi.ts            # 【前端 Mock 服务】统一暴露出模拟 API 方法，方便无缝过渡到未来可能启用的后端 API
 │
 └─ pages/                          # SEO 国家独立页面，每个页面都绑定了独立的元数据
     ├─ index.vue                   # 聚合首页
     ├─ us-address-generator.vue    # 美国专属页面
     ├─ japan-address-generator.vue  # 日本专属页面
     └─ ...                         # 其他国家页面
```

---

## 📜 地址生成规则 (Address Engine Rules)

目前我们支持 7 个主流国家的随机地址生成，且算法尽可能保证了**格式真实、结构合理、字段匹配**，具体逻辑见 `frontend/composables/useAddressGenerator.ts`：

1.  **美国 (US)**：确保州（State）和城市（City）是一对一对应的，并且 ZIP Code（邮编）和 Area Code（电话区号）与该州尽可能匹配，看起来像是一个真人在美国居住！
2.  **日本 (JP)**：生成符合日本传统的都道府县、市区町村、丁目番地结构，格式如 `〒150-0002 東京都渋谷区渋谷 1-2-3`。
3.  **英国 (UK)**：使用超逼真的邮政编码（Postcode）随机算法（例如 `SW1A 1AA`），并组合出英格兰、苏格兰、威尔士等风格的地址。
4.  **加拿大 (CA)**：邮编采用经典的 `A1A 1A1` 交叉字母数字格式，且省份与城市严格绑定。
5.  **澳大利亚 (AU)**：自动生成匹配的 Suburb (郊区/城市)、State (州) 和 4 位数字的 Postcode。
6.  **土耳其 (TR)**：支持 Mahalle (街区)、District (区)、Province (省) 的三级对应，以及 5 位邮编和 `+90` 格式电话。
7.  **尼日利亚 (NG)**：支持 LGA (地方政府区)、State 对应，以及 6 位数字的尼日利亚邮编和 `+234` 格式电话。

---

## 🔍 SEO 与收录黑科技 (SEO Strategies)

为了让 Google 爬虫疯狂爱上这个网站，我们在前端 Nuxt 3 层面做了如下优化：

1.  **国家独立页面 (URL Isolation)**：每个国家都有自己独立的 URL（例如 `/us-address-generator`），拒绝一页流，最大化长尾关键词覆盖率！
2.  **独立 Meta 信息**：每个页面都动态注入了专有的 `title`、`meta description` 和 `h1`，完美融入对应国家的外贸、软件测试搜索关键词。
3.  **FAQ 结构化数据 (Schema)**：为每一页自动生成 Google 喜爱的 `FAQPage` 结构化 JSON-LD 标签，直接抢占 Google 搜索结果的富媒体展示位。
4.  **Sitemap & Robots**：利用 `@nuxtjs/sitemap` 模块自动在本地构建时扫描路由，生成合规的 `sitemap.xml` 和 `robots.txt`。

---

## 🔒 合规与风控温（保）馨（命）提示

由于我们生成的是随机测试数据，页面文案及代码编写时必须**死守以下红线**：

*   **必须声明**：所有生成结果仅供 `software testing, form testing, and educational use only`（软件测试、表单测试及教学演示使用）。
*   **禁止词汇**：所有页面、FAQ、内链文案中，**绝对不能**出现任何引导用户去“注册账号”、“绕过平台风控”、“过敏感验证”等作死字眼。我们要做一个纯洁的、合规的、薅 Google 广告费的绿黄色工具站！💸

---

## 🚀 后续项目发展规划 (`backend` 启用计划)

如果网站日 PV 冲上 10W，我们将按以下步骤重启 `backend`：
1.  **迁移规则引擎**：将 `frontend/composables/useAddressGenerator.ts` 翻译回 `backend/address-generator-api` 里的 Java Class，由 Spring Boot 跑在云端。
2.  **加入数据库**：将静态的 JSON 地址库转换为数据库表，引入地理位置坐标、生成轨迹缓存等进阶数据。
3.  **开发 API 接口与授权**：引入 Spring Security / JWT，针对高频使用地址生成服务的企业客户进行 API 限流与收费。

> 祝大家早日靠 SEO 流量实现睡后收入翻倍！如果有任何关于生成器算法的问题，欢迎直接在 `frontend` 代码里 debug！👋
