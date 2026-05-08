# GlobalAddressHub 一期可执行清单

> 基于 `GlobalAddressHub.md` 和参考图整理。执行时前后端目录分开：前端使用 `frontend/`，后端使用 `backend/address-generator-api/`。

## 0. 范围边界

- [ ] 一期只做多国随机地址生成工具站，不做登录、用户系统、支付、API Key、管理后台、微服务、Redis 集群、复杂数据库。
- [ ] 一期国家范围固定为 `US`、`JP`、`UK`、`CA`、`AU`、`TR`、`NG`。
- [ ] 页面文案统一强调 `For software testing, form testing, and educational use only.`。
- [ ] 不在页面、Meta、FAQ、按钮、导航、结构化数据中出现禁用词。
- [ ] 参考图中的 FAQ “account registration” 与合规要求冲突，不能照抄。

## 1. 目录规划

```text
global-address-hub/
 ├─ frontend/
 │   └─ Nuxt 3 + Vue 3 + TypeScript + TailwindCSS
 │
 ├─ backend/
 │   └─ address-generator-api/
 │       └─ Spring Boot 3 + Java 17 + Maven
 │
 ├─ docker-compose.yml
 ├─ GlobalAddressHub.md
 └─ GlobalAddressHub-executable-checklist.md
```

验收：

- [ ] 前端和后端可以分别启动、分别构建。
- [ ] 前端只通过 HTTP API 调用后端，不直接读取后端 JSON 数据文件。
- [ ] 后端不依赖前端运行。

---

# 前端清单：`frontend/`

## F-01. 初始化 Nuxt 3 工程

目标文件：

```text
frontend/package.json
frontend/nuxt.config.ts
frontend/app.vue
frontend/assets/css/main.css
frontend/tailwind.config.ts
frontend/tsconfig.json
```

任务：

- [ ] 创建 Nuxt 3 + Vue 3 + TypeScript 项目。
- [ ] 接入 TailwindCSS。
- [ ] 设置默认主题颜色：`#2563EB`、`#10B981`、`#F8FAFC`、`#0F172A`、`#E2E8F0`。
- [ ] 配置运行时 API 地址：`public.apiBaseUrl`。

验收：

- [ ] `npm install` 成功。
- [ ] `npm run dev` 可启动。
- [ ] `npm run build` 可通过。

## F-02. 建立基础布局

目标文件：

```text
frontend/components/Header.vue
frontend/components/Footer.vue
frontend/layouts/default.vue
```

任务：

- [ ] Header 包含品牌 `GlobalAddressHub`、导航、国家入口、FAQ、Contact、语言入口占位。
- [ ] Footer 包含 Popular Countries、Resources、Legal、Contact。
- [ ] Footer 必须包含 `Privacy Policy`、`Terms of Use`、`Disclaimer`。
- [ ] PC 优先布局，移动端不重叠、不溢出。

验收：

- [ ] 所有页面共用同一套 Header/Footer。
- [ ] 导航链接指向真实页面。
- [ ] 页面没有无效路由入口。

## F-03. 实现地址生成器组件

目标文件：

```text
frontend/components/CountrySelector.vue
frontend/components/RegionSelector.vue
frontend/components/AddressGenerator.vue
frontend/components/AddressResultCard.vue
frontend/components/CopyButton.vue
frontend/types/address.ts
frontend/composables/useAddressApi.ts
```

任务：

- [ ] 调用 `GET /api/countries` 获取国家列表。
- [ ] 选择国家后调用 `GET /api/regions?country={countryCode}` 获取地区列表。
- [ ] 点击生成按钮调用 `GET /api/address/random?country={countryCode}`。
- [ ] 选择地区时调用 `GET /api/address/random?country={countryCode}&region={regionCode}`。
- [ ] 结果卡片展示 `fullName`、`street`、`city`、`regionName`、`postalCode`、`phone`、`country`、`fullAddress`。
- [ ] 实现 `Copy All`：复制完整地址。
- [ ] 实现 `Copy in Lines`：按字段换行复制。
- [ ] 请求失败时展示 API 返回的 `message`，不展示浏览器原始错误。

验收：

- [ ] 国家、地区、生成按钮在桌面首屏可见。
- [ ] 生成结果和复制按钮在桌面首屏可见。
- [ ] 复制成功后有轻量反馈，例如按钮文字短暂变为 `Copied`。
- [ ] 后端 400、429、500 时前端显示可理解错误文案。

## F-04. 首页

目标文件：

```text
frontend/pages/index.vue
frontend/components/FeatureGrid.vue
frontend/components/FAQSection.vue
frontend/components/SeoContent.vue
```

任务：

- [ ] 首屏采用左右分栏：左侧 Hero，右侧生成器卡片和结果卡片。
- [ ] Hero 包含工具定位、可信说明、热门国家入口。
- [ ] FeatureGrid 展示 Multiple Countries、Realistic & Valid、Fast & Free、Privacy First。
- [ ] FAQ 只解释地址格式、字段含义、测试用途、复制方式、数据是否真实。
- [ ] SEO 正文放在首屏下方。

验收：

- [ ] 首页 URL `/` 可访问。
- [ ] 首屏核心操作完整可用。
- [ ] FAQ 不出现禁用词。
- [ ] 视觉接近参考图：白底、卡片、蓝色主按钮、轻量工具站风格。

## F-05. 7 个国家独立页面

目标文件：

```text
frontend/pages/us-address-generator.vue
frontend/pages/japan-address-generator.vue
frontend/pages/uk-address-generator.vue
frontend/pages/canada-address-generator.vue
frontend/pages/australia-address-generator.vue
frontend/pages/turkey-address-generator.vue
frontend/pages/nigeria-address-generator.vue
frontend/data/country-pages.ts
```

任务：

- [ ] 每个国家页绑定固定默认国家。
- [ ] 每个国家页包含独立 `Title`、`Meta Description`、`H1`、Hero Copy、FAQ。
- [ ] 每个国家页包含独立 canonical。
- [ ] 每个国家页输出 FAQPage 结构化数据。
- [ ] 页面正文围绕对应国家地址格式写，不共用完全相同正文。

验收：

- [ ] 7 个 URL 全部可访问。
- [ ] 页面源代码中能看到对应国家的 Meta、H1、canonical。
- [ ] 国家页默认生成对应国家地址。
- [ ] FAQ 内容不出现禁用词。

## F-06. 法务与合规页面

目标文件：

```text
frontend/pages/privacy-policy.vue
frontend/pages/terms-of-use.vue
frontend/pages/disclaimer.vue
frontend/pages/contact.vue
```

任务：

- [ ] Privacy Policy 说明不收集个人地址数据，使用 Google Analytics / AdSense 时说明 Cookie。
- [ ] Terms of Use 限定用途为 software testing、QA、form validation、educational use、demo data。
- [ ] Disclaimer 说明生成地址为算法生成或样例数据，不代表真实个人身份。
- [ ] Contact 提供联系邮箱占位，例如 `contact@yourdomain.com`。

验收：

- [ ] 四个页面均可访问。
- [ ] Footer Legal 链接全部有效。
- [ ] 文案不包含禁用词。

## F-07. SEO 配置

目标文件：

```text
frontend/nuxt.config.ts
frontend/public/robots.txt
frontend/sitemap.config.ts
```

任务：

- [ ] 配置 `/sitemap.xml`。
- [ ] 配置 `robots.txt`。
- [ ] 为首页和 7 个国家页配置 canonical。
- [ ] 配置 Open Graph 基础信息。
- [ ] 预留 Google Analytics ID 配置项。

验收：

- [ ] `/sitemap.xml` 可访问，包含首页、7 个国家页、4 个法务页面。
- [ ] `/robots.txt` 可访问，包含 Sitemap 地址。
- [ ] 构建产物不包含无效域名 canonical。

## F-08. 前端验证命令

执行命令：

```powershell
cd E:\dev\IdeaProjects\more-more-money\global-address-hub\frontend
npm install
npm run dev
npm run build
```

验收：

- [ ] Dev 环境首页可打开。
- [ ] 7 个国家页可打开。
- [ ] 生产构建成功。
- [ ] 浏览器控制台无明显错误。

---

# 后端清单：`backend/address-generator-api/`

## B-01. 初始化 Spring Boot API 工程

目标文件：

```text
backend/address-generator-api/pom.xml
backend/address-generator-api/src/main/java/com/globaladdresshub/address/AddressGeneratorApiApplication.java
backend/address-generator-api/src/main/resources/application.yml
```

任务：

- [ ] 使用 Java 17。
- [ ] 使用 Spring Boot 3。
- [ ] 引入 `spring-boot-starter-web`、Jackson、Lombok、validation、test。
- [ ] 配置服务端口 `8080`。
- [ ] 配置 CORS 允许前端域名和本地开发地址。

验收：

- [ ] Maven 编译通过。
- [ ] `/actuator` 不作为一期强依赖，除非后续明确接入。
- [ ] 启动后无数据库连接依赖。

## B-02. 建立模型和枚举

目标文件：

```text
backend/address-generator-api/src/main/java/com/globaladdresshub/address/model/AddressResult.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/model/CountryCode.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/model/GenerateRequest.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/model/CountryOption.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/model/RegionOption.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/model/ApiErrorResponse.java
```

任务：

- [ ] `CountryCode` 只允许 `US`、`JP`、`UK`、`CA`、`AU`、`TR`、`NG`。
- [ ] `GenerateRequest` 支持 `country` 和可选 `region`。
- [ ] `AddressResult` 统一字段命名，前端不需要针对国家做复杂适配。
- [ ] `ApiErrorResponse` 包含 `success`、`code`、`message`、`requestId`。

验收：

- [ ] 所有 API 返回 JSON。
- [ ] 错误响应不返回 HTML。

## B-03. 准备 JSON 地址数据

目标文件：

```text
backend/address-generator-api/src/main/resources/data/us-address.json
backend/address-generator-api/src/main/resources/data/jp-address.json
backend/address-generator-api/src/main/resources/data/uk-address.json
backend/address-generator-api/src/main/resources/data/ca-address.json
backend/address-generator-api/src/main/resources/data/au-address.json
backend/address-generator-api/src/main/resources/data/tr-address.json
backend/address-generator-api/src/main/resources/data/ng-address.json
```

任务：

- [ ] 每个国家至少准备 5 个一级地区。
- [ ] 每个一级地区至少准备 3 个城市、区、县或 LGA。
- [ ] 每个城市至少准备 2 个邮编或邮编模式。
- [ ] 每个国家准备 firstNames、lastNames、streetNames。
- [ ] 数据只用于生成测试样例，不放真实个人完整地址。

验收：

- [ ] JSON 能被 Jackson 正常解析。
- [ ] 数据字段满足各国家规则。
- [ ] 缺失或损坏数据会返回 `GENERATION_FAILED`。

## B-04. 实现国家和地区查询接口

目标文件：

```text
backend/address-generator-api/src/main/java/com/globaladdresshub/address/controller/AddressController.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/service/CountryRuleService.java
```

接口：

```http
GET /api/countries
GET /api/regions?country=US
```

任务：

- [ ] `/api/countries` 返回 7 个国家。
- [ ] `/api/regions` 校验 `country` 参数。
- [ ] 不支持国家返回 `UNSUPPORTED_COUNTRY`。
- [ ] 国家参数为空或格式错误返回 `INVALID_COUNTRY`。

验收：

- [ ] `/api/countries` 可被 CDN 缓存。
- [ ] `/api/regions` 可被 CDN 缓存。
- [ ] 返回结构稳定，前端可直接渲染下拉框。

## B-05. 实现地址规则接口

目标文件：

```text
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/AddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/UsAddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/JapanAddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/UkAddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/CanadaAddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/AustraliaAddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/TurkeyAddressRule.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/rule/NigeriaAddressRule.java
```

任务：

- [ ] US：州、城市、ZIP、电话区号尽量匹配。
- [ ] JP：输出邮便番号、都道府县、市区町村、丁目番地、电话。
- [ ] UK：Postcode 格式接近 `SW1A 1AA`。
- [ ] CA：Postal Code 格式为 `A1A 1A1`。
- [ ] AU：State、Suburb、Postcode、Phone 匹配。
- [ ] TR：Province、District、5 位 Postal Code、`+90` 电话格式。
- [ ] NG：State、City 或 LGA、6 位 Postal Code、`+234` 电话格式。

验收：

- [ ] 每个国家可随机生成至少 20 次不报错。
- [ ] 指定地区时结果地区匹配请求参数。
- [ ] 输出 `fullAddress` 可直接复制使用。

## B-06. 实现随机地址 API

目标文件：

```text
backend/address-generator-api/src/main/java/com/globaladdresshub/address/controller/AddressController.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/service/AddressGenerateService.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/service/CopyFormatService.java
```

接口：

```http
GET /api/address/random?country=US
GET /api/address/random?country=US&region=CA
```

任务：

- [ ] `country` 必填。
- [ ] `region` 可选。
- [ ] country 大小写兼容，内部统一转大写。
- [ ] region 格式错误返回 `INVALID_REGION`。
- [ ] region 不属于当前国家返回 `UNSUPPORTED_REGION`。
- [ ] 地址生成失败返回 `GENERATION_FAILED`。
- [ ] 设置响应头禁止 CDN 缓存。

验收：

- [ ] 正常请求返回 `AddressResult`。
- [ ] 错误请求返回统一 JSON。
- [ ] `/api/address/random` 不被缓存。

## B-07. 统一错误处理

目标文件：

```text
backend/address-generator-api/src/main/java/com/globaladdresshub/address/exception/ApiException.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/exception/ApiErrorCode.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/exception/GlobalExceptionHandler.java
```

任务：

- [ ] 实现错误码：`INVALID_COUNTRY`、`UNSUPPORTED_COUNTRY`、`INVALID_REGION`、`UNSUPPORTED_REGION`、`GENERATION_FAILED`、`RATE_LIMITED`。
- [ ] 400 用于参数错误、国家或地区不支持。
- [ ] 429 用于限流。
- [ ] 500 用于生成失败或数据加载失败。
- [ ] 每个错误响应包含 `requestId`。

验收：

- [ ] 不存在 Spring Boot 默认 HTML 错误页。
- [ ] 异常日志中能通过 `requestId` 定位请求。

## B-08. 限流和 CORS

目标文件：

```text
backend/address-generator-api/src/main/java/com/globaladdresshub/address/config/CorsConfig.java
backend/address-generator-api/src/main/java/com/globaladdresshub/address/config/RateLimitConfig.java
```

任务：

- [ ] 本地允许 `http://localhost:3000`。
- [ ] 生产允许正式域名。
- [ ] 对 `/api/address/random` 做基础限流。
- [ ] 触发限流返回 `RATE_LIMITED` 和 HTTP 429。

验收：

- [ ] 前端本地能正常调用 API。
- [ ] 高频请求能被限制。

## B-09. 后端测试

目标文件：

```text
backend/address-generator-api/src/test/java/com/globaladdresshub/address/controller/AddressControllerTest.java
backend/address-generator-api/src/test/java/com/globaladdresshub/address/service/AddressGenerateServiceTest.java
backend/address-generator-api/src/test/java/com/globaladdresshub/address/rule/AddressRuleTest.java
```

任务：

- [ ] 测试 `/api/countries` 返回 7 个国家。
- [ ] 测试 `/api/regions?country=US` 返回美国地区。
- [ ] 测试 7 个国家随机生成均成功。
- [ ] 测试指定 region 生成结果匹配。
- [ ] 测试空 country 返回 `INVALID_COUNTRY`。
- [ ] 测试不支持 country 返回 `UNSUPPORTED_COUNTRY`。
- [ ] 测试不支持 region 返回 `UNSUPPORTED_REGION`。
- [ ] 测试错误响应为 JSON。

验收：

- [ ] 单元测试通过。
- [ ] 集成测试通过。
- [ ] Maven package 成功。

## B-10. 后端验证命令

执行命令：

```powershell
cd E:\dev\IdeaProjects\more-more-money\global-address-hub\backend\address-generator-api
E:\dev\apache-maven-3.9.6\bin\mvn.cmd -Dmaven.repo.local=E:\dev\apache-maven-3.9.6\Repository test
E:\dev\apache-maven-3.9.6\bin\mvn.cmd -Dmaven.repo.local=E:\dev\apache-maven-3.9.6\Repository package
```

手工接口验证：

```powershell
curl.exe "http://localhost:8080/api/countries"
curl.exe "http://localhost:8080/api/regions?country=US"
curl.exe "http://localhost:8080/api/address/random?country=US"
curl.exe "http://localhost:8080/api/address/random?country=US&region=CA"
curl.exe "http://localhost:8080/api/address/random?country=XX"
```

验收：

- [ ] `test` 成功。
- [ ] `package` 成功。
- [ ] 正常接口返回业务 JSON。
- [ ] 错误接口返回统一错误 JSON。

---

# 联调与部署清单

## D-01. Docker Compose

目标文件：

```text
docker-compose.yml
frontend/Dockerfile
backend/address-generator-api/Dockerfile
```

任务：

- [ ] frontend 服务监听 `3000`。
- [ ] backend 服务监听 `8080`。
- [ ] frontend 通过 `API_BASE_URL=http://backend:8080` 调用后端。
- [ ] 不引入数据库容器。

验收：

- [ ] `docker compose up -d --build` 成功。
- [ ] 浏览器打开前端首页可生成地址。
- [ ] 后端 API 容器日志无启动错误。

## D-02. 上线前 SEO 验收

- [ ] 首页可访问。
- [ ] 7 个国家页可访问。
- [ ] 4 个法务页面可访问。
- [ ] `/sitemap.xml` 可访问。
- [ ] `/robots.txt` 可访问。
- [ ] 每个国家页有独立 Title、Meta Description、H1、canonical、FAQPage JSON-LD。
- [ ] 页面禁止词扫描通过。
- [ ] Google Search Console 已添加正式域名。
- [ ] Google Analytics 已配置。
- [ ] 正式域名 HTTPS 可访问。

## D-03. MVP 上线标准

- [ ] 首页可访问。
- [ ] 7 个国家页可访问。
- [ ] 能随机生成地址。
- [ ] 能指定地区生成地址。
- [ ] 能一键复制。
- [ ] 有 FAQ。
- [ ] 有 Privacy Policy。
- [ ] 有 Terms of Use。
- [ ] 有 Disclaimer。
- [ ] 有 Contact。
- [ ] API 有统一错误响应。
- [ ] 合规禁用词已检查。
- [ ] 有 sitemap.xml。
- [ ] 接入 Search Console。
- [ ] 正式域名可访问。

## D-04. 建议提交顺序

```text
1. chore: initialize frontend project
2. feat: add frontend layout and static pages
3. feat: add address generator UI
4. chore: initialize backend api project
5. feat: add country and region APIs
6. feat: add address generation rules
7. feat: connect frontend with backend API
8. feat: add SEO metadata and legal pages
9. chore: add docker compose deployment
10. test: add MVP verification coverage
```

