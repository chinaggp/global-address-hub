# 多国地址生成器（一期）技术方案

## 一、项目定位

项目名称：GlobalAddressHub

项目定位：

> 多国地址生成器 / 测试数据生成工具站 / SEO 工具站

一期目标：

- 快速上线 MVP
- 验证 Google SEO 收录
- 验证自然流量
- 验证用户复制行为
- 验证 AdSense 可行性

一期不做：

- 登录
- 用户系统
- 支付
- API Key
- 管理后台
- 微服务
- Redis 集群
- 复杂数据库

---

# 二、整体架构

```text
用户浏览器
   ↓
Cloudflare CDN
   ↓
Nuxt 3 前端
   ↓
Spring Boot API
   ↓
JSON 地址数据 / 规则引擎
```

---

# 三、前端技术方案

## 1. 技术栈

```text
Nuxt 3
Vue 3
TypeScript
TailwindCSS
Pinia（可选）
i18n（后期）
```

推荐原因：

- SEO 友好
- 支持 SSR / SSG
- 页面生成能力强
- 多语言扩展方便
- 非常适合工具站

---

## 2. 页面结构

一期建议页面：

```text
/
首页

/us-address-generator
美国地址生成器

/japan-address-generator
日本地址生成器

/uk-address-generator
英国地址生成器

/canada-address-generator
加拿大地址生成器

/australia-address-generator
澳大利亚地址生成器

/turkey-address-generator
土耳其地址生成器

/nigeria-address-generator
尼日利亚地址生成器

/privacy-policy
隐私政策

/terms-of-use
使用条款

/disclaimer
免责声明

/contact
联系页面
```

---

## 3. 首页模块结构

```text
Header 顶部导航
Hero 首屏介绍
Address Generator 生成器区域
Generated Result 结果卡片
Features 功能介绍
Popular Countries 热门国家
FAQ 问答区
Footer 底部信息
```

---

## 4. 前端组件设计

```text
components/
 ├─ Header.vue
 ├─ Footer.vue
 ├─ CountrySelector.vue
 ├─ StateSelector.vue
 ├─ AddressGenerator.vue
 ├─ AddressResultCard.vue
 ├─ CopyButton.vue
 ├─ FeatureGrid.vue
 ├─ FAQSection.vue
 └─ SeoContent.vue
```

---

## 5. 页面风格

推荐主题颜色：

```text
主色：#2563EB
辅助色：#10B981
背景色：#F8FAFC
文字色：#0F172A
边框色：#E2E8F0
```

风格关键词：

```text
专业
简洁
工具站
开发者友好
PC 优先
轻量可信
```

页面特点：

- 白底
- 卡片式布局
- 大量留白
- 蓝色主按钮
- 快速加载
- 复制操作明显
- SEO 内容丰富

PC 优先布局要求：

```text
首屏采用左右分栏：
左侧：品牌价值、可信说明、热门国家入口
右侧：地址生成器卡片 + 生成结果卡片

桌面端优先保证：
1. 国家选择、地区选择、生成按钮在首屏可见
2. 生成结果和 Copy All / Copy in Lines 操作在首屏可见
3. FAQ 和 SEO 正文位于首屏下方，服务搜索引擎收录
```

---

# 四、后端技术方案

## 1. 技术栈

```text
Java 17
Spring Boot 3
Maven
Jackson
Lombok
Docker
```

一期不强制数据库。

---

## 2. 后端目录结构

```text
address-generator-api/
 ├─ controller/
 │   └─ AddressController.java
 │
 ├─ service/
 │   ├─ AddressGenerateService.java
 │   ├─ CountryRuleService.java
 │   └─ CopyFormatService.java
 │
 ├─ rule/
 │   ├─ AddressRule.java
 │   ├─ UsAddressRule.java
 │   ├─ JapanAddressRule.java
 │   ├─ UkAddressRule.java
 │   ├─ CanadaAddressRule.java
 │   ├─ AustraliaAddressRule.java
 │   ├─ TurkeyAddressRule.java
 │   └─ NigeriaAddressRule.java
 │
 ├─ model/
 │   ├─ AddressResult.java
 │   ├─ CountryCode.java
 │   └─ GenerateRequest.java
 │
 ├─ data/
 │   ├─ us-address.json
 │   ├─ jp-address.json
 │   ├─ uk-address.json
 │   ├─ ca-address.json
 │   ├─ au-address.json
 │   ├─ tr-address.json
 │   └─ ng-address.json
 │
 └─ config/
     ├─ CorsConfig.java
     └─ RateLimitConfig.java
```

---

# 五、核心接口设计

## 1. 随机生成地址

```http
GET /api/address/random?country=US
```

返回示例：

```json
{
  "country": "United States",
  "countryCode": "US",
  "fullName": "John David Smith",
  "street": "742 Evergreen Terrace",
  "city": "Los Angeles",
  "state": "California",
  "postalCode": "90001",
  "phone": "+1 213-555-0187",
  "fullAddress": "742 Evergreen Terrace, Los Angeles, CA 90001, United States"
}
```

---

## 2. 指定州生成

```http
GET /api/address/random?country=US&state=CA
```

---

## 3. 获取国家列表

```http
GET /api/countries
```

---

## 4. 获取州 / 省列表

```http
GET /api/regions?country=US
```

---

## 5. API 错误响应

所有 API 错误统一返回 JSON，不返回 HTML 错误页。

```json
{
  "success": false,
  "code": "UNSUPPORTED_COUNTRY",
  "message": "Unsupported country code.",
  "requestId": "20260508103000123"
}
```

一期错误码：

```text
INVALID_COUNTRY
country 参数为空或格式错误

UNSUPPORTED_COUNTRY
country 不在 US / JP / UK / CA / AU / TR / NG 范围内

INVALID_REGION
state / region 参数格式错误

UNSUPPORTED_REGION
state / region 不属于当前 country

GENERATION_FAILED
地址生成失败或数据文件不可用

RATE_LIMITED
请求过于频繁
```

HTTP 状态码约定：

```text
400 Bad Request
参数为空、格式错误、国家或地区不支持

429 Too Many Requests
触发限流

500 Internal Server Error
服务端生成失败、数据文件加载失败
```

缓存要求：

```text
/api/address/random 禁止 CDN 缓存
/api/countries 可缓存
/api/regions 可缓存
```

---

# 六、地址规则设计

一期重点：

> 格式真实、结构合理、字段匹配。

---

## 美国规则

字段：

```text
姓名
街道
城市
州
ZIP
电话
```

要求：

```text
州和城市匹配
ZIP 与州尽量匹配
电话区号合理
```

---

## 日本规则

字段：

```text
姓名
邮便番号
都道府县
市区町村
丁目番地
电话
```

示例：

```text
〒150-0002
東京都渋谷区渋谷 1-2-3
```

---

## 英国规则

重点：

```text
Postcode 格式真实
例如：SW1A 1AA
```

---

## 加拿大规则

邮编格式：

```text
A1A 1A1
```

---

## 澳大利亚规则

字段：

```text
State
Suburb
Postcode
Phone
```

---

## 土耳其规则

字段：

```text
Full Name
Mahalle / Street
District
Province
Postal Code
Phone
```

要求：

```text
Province 与 District 尽量匹配
Postal Code 使用 5 位数字格式
Phone 使用土耳其手机号或固定电话格式，例如 +90 5xx xxx xx xx
```

---

## 尼日利亚规则

字段：

```text
Full Name
Street
City / Local Government Area
State
Postal Code
Phone
```

要求：

```text
State 与 City / LGA 尽量匹配
Postal Code 使用 6 位数字格式
Phone 使用尼日利亚手机号格式，例如 +234 80x xxx xxxx
```

---

# 七、数据结构设计

一期使用 JSON 文件。

示例：

```json
{
  "states": [
    {
      "code": "CA",
      "name": "California",
      "cities": [
        {
          "name": "Los Angeles",
          "zipCodes": ["90001", "90002"],
          "areaCodes": ["213", "323"]
        }
      ]
    }
  ],
  "streetNames": [
    "Main St",
    "Oak Ave",
    "Maple Dr"
  ],
  "firstNames": [
    "John",
    "Michael",
    "David"
  ],
  "lastNames": [
    "Smith",
    "Johnson",
    "Brown"
  ]
}
```

---

# 八、SEO 技术方案

## 1. 国家独立页面

必须：

```text
/us-address-generator
/japan-address-generator
/uk-address-generator
/canada-address-generator
/australia-address-generator
/turkey-address-generator
/nigeria-address-generator
```

不要只做一个生成页。

---

## 2. 独立 Meta 信息

每个国家页必须具备独立的：

```text
URL
Title
Meta Description
H1
首屏说明文案
FAQ
结构化数据 FAQPage
canonical
```

SEO 模板如下。

### United States

```text
URL:
/us-address-generator

Title:
Random US Address Generator - Free United States Address Tool

Meta Description:
Generate random United States addresses with ZIP code, city, state, and phone number for software testing, QA, and form validation.

H1:
Random US Address Generator

Hero Copy:
Generate realistic United States address data with state, city, ZIP code, and phone number formatting for testing and educational use.

FAQ:
What is a US address format?
Can I generate an address for a specific state?
Are these generated US addresses real?
Can I use this for software testing?
```

### Japan

```text
URL:
/japan-address-generator

Title:
Random Japan Address Generator - Free Japanese Address Tool

Meta Description:
Generate random Japanese addresses with postal code, prefecture, city, street, and phone number formatting for testing and form validation.

H1:
Random Japan Address Generator

Hero Copy:
Create realistic Japanese address samples with postal code, prefecture, city, and local address fields for QA and educational scenarios.

FAQ:
What is a Japanese address format?
Does the generator include Japanese postal codes?
Are generated Japan addresses real?
Can I copy the generated address for test data?
```

### United Kingdom

```text
URL:
/uk-address-generator

Title:
Random UK Address Generator - Free United Kingdom Address Tool

Meta Description:
Generate random United Kingdom addresses with postcode, town, county, and phone number formatting for testing, QA, and form validation.

H1:
Random UK Address Generator

Hero Copy:
Generate UK-style address data with realistic postcode formatting, town, street, and phone fields for software testing and demos.

FAQ:
What is a UK postcode format?
Does this tool generate England, Scotland, Wales, and Northern Ireland style data?
Are generated UK addresses real?
Can I use these addresses in test forms?
```

### Canada

```text
URL:
/canada-address-generator

Title:
Random Canada Address Generator - Free Canadian Address Tool

Meta Description:
Generate random Canadian addresses with province, city, postal code, and phone number formatting for software testing and form validation.

H1:
Random Canada Address Generator

Hero Copy:
Create Canadian address samples with province, city, postal code, street, and phone fields for test data and educational use.

FAQ:
What is a Canadian postal code format?
Can I generate an address by province?
Are generated Canadian addresses real?
Can I copy Canadian address data in one click?
```

### Australia

```text
URL:
/australia-address-generator

Title:
Random Australia Address Generator - Free Australian Address Tool

Meta Description:
Generate random Australian addresses with state, suburb, postcode, and phone number formatting for QA, software testing, and form validation.

H1:
Random Australia Address Generator

Hero Copy:
Generate Australian address data with suburb, state, postcode, street, and phone formatting for testing workflows and demo content.

FAQ:
What is an Australian address format?
Does this generator include Australian postcodes?
Are generated Australian addresses real?
Can I use this for QA and form testing?
```

### Turkey

```text
URL:
/turkey-address-generator

Title:
Random Turkey Address Generator - Free Turkish Address Tool

Meta Description:
Generate random Turkey addresses with province, district, postal code, street, and phone number formatting for software testing and QA.

H1:
Random Turkey Address Generator

Hero Copy:
Create Turkey address samples with province, district, postal code, street, and phone fields for test forms and educational demos.

FAQ:
What is a Turkey address format?
Does this tool include Turkish provinces and districts?
Are generated Turkey addresses real?
Can I copy Turkish address data for software testing?
```

### Nigeria

```text
URL:
/nigeria-address-generator

Title:
Random Nigeria Address Generator - Free Nigerian Address Tool

Meta Description:
Generate random Nigerian addresses with state, city, postal code, street, and phone number formatting for software testing and validation.

H1:
Random Nigeria Address Generator

Hero Copy:
Generate Nigerian address samples with state, city or local government area, postal code, street, and phone fields for QA and demos.

FAQ:
What is a Nigerian address format?
Does this generator include Nigerian states?
Are generated Nigerian addresses real?
Can I use Nigerian address samples for test data?
```

---

## 3. FAQ Schema

示例 FAQ：

```text
What is a US address format?
Are these generated addresses real?
Can I use this for software testing?
```

---

## 4. sitemap.xml

Nuxt 自动生成：

```text
/sitemap.xml
```

---

## 5. robots.txt

```text
User-agent: *
Allow: /

Sitemap: https://yourdomain.com/sitemap.xml
```

---

# 九、合规与风控

页面文案必须强调：

```text
For software testing, form testing, and educational use only.
```

所有页面、Meta、FAQ、按钮、导航、结构化数据、博客内链文案禁止出现：

```text
注册账号
账号注册
注册平台
绕过验证
绕过风控
虚假身份
身份伪造
实名认证
信用卡
银行卡
接码
短信验证
手机号验证
薅羊毛
套利
欺诈
诈骗
匿名开户
KYC
```

必须具备页面：

```text
Privacy Policy
Terms of Use
Disclaimer
Contact
```

合规文案边界：

```text
允许：
software testing
QA
form validation
educational use
demo data
frontend testing
database seed data

不允许：
account registration
bypass verification
fake identity
receive SMS
credit card
financial account
avoid risk control
```

FAQ 规则：

```text
FAQ 只能解释地址格式、字段含义、测试用途、复制方式、数据是否真实。
不要用“能不能注册账号”“能不能绕过验证”这类问题做 FAQ，即使答案是否定的，也会给搜索引擎和广告审核错误信号。
```

---

# 十、部署方案

## 推荐部署架构

```text
Cloudflare
  ↓
Nginx
  ↓
Nuxt 3
  ↓
Spring Boot
```

---

## Docker Compose 示例

```yaml
services:
  frontend:
    image: address-web:latest
    ports:
      - "3000:3000"
    environment:
      - API_BASE_URL=http://backend:8080

  backend:
    image: address-api:latest
    ports:
      - "8080:8080"
```

---

# 十一、第三方服务

推荐接入：

```text
Cloudflare
Google Analytics
Google Search Console
Google AdSense
```

后期可选：

```text
MaxMind GeoIP
Google Tag Manager
```

---

# 十二、一期开发顺序

## 第一步：前端静态页面

先完成：

```text
首页
美国页
日本页
英国页
加拿大页
澳大利亚页
土耳其页
尼日利亚页
```

---

## 第二步：后端 API

实现：

```text
/api/address/random?country=US
/api/address/random?country=JP
/api/address/random?country=UK
/api/address/random?country=CA
/api/address/random?country=AU
/api/address/random?country=TR
/api/address/random?country=NG
/api/countries
/api/regions?country={countryCode}
```

---

## 第三步：地址规则

优先支持：

```text
US
JP
UK
CA
AU
TR
NG
```

---

## 第四步：SEO

完成：

```text
Meta
FAQ
sitemap
robots
结构化数据
```

---

## 第五步：部署上线

完成：

```text
Cloudflare
HTTPS
Google Search Console
Google Analytics
```

---

# 十三、二期扩展规划

```text
MySQL 管理地址数据
Redis 缓存
IP 自动识别国家
批量生成
CSV 导出
API Key
多语言
广告位
博客文章
更多国家页面
```

---

# 十四、一期最小交付标准

只要完成下面这些即可上线：

```text
1. 首页可访问
2. 7 个国家页可访问
3. 能随机生成地址
4. 能一键复制
5. 有 FAQ
6. 有隐私政策
7. 有使用条款
8. 有免责声明
9. API 有统一错误响应
10. 合规禁用词已检查
11. 有 sitemap.xml
12. 接入 Search Console
13. 正式域名可访问
```

---

# 十五、最终建议

一期核心原则：

> 先上线、先收录、先拿流量。

不要过度设计。

推荐最终一期技术组合：

```text
Nuxt 3 + TailwindCSS
Spring Boot 3
JSON 数据文件
Cloudflare
Docker Compose
```

这是目前最适合个人开发者的 SEO 工具站方案。
