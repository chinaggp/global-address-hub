export interface BlogBlock {
  type: 'p' | 'h2' | 'h3' | 'ul'
  text?: string
  items?: string[]
}

export interface LocalizedBlogPost {
  title: string
  description: string
  content: BlogBlock[]
}

export const blogContentEn: Record<string, LocalizedBlogPost> = {
  'importance-of-mock-address-data-in-qa': {
    title: 'The Importance of Mock Address Data in QA Testing',
    description: 'Learn why realistic mock address data is crucial for testing modern web applications and avoiding form validation bugs.',
    content: [
      { type: 'p', text: "When building web applications that ship products, process payments, or register users, form validation is one of the most critical touchpoints. Yet, it is often one of the least thoroughly tested. Developer teams frequently use generic placeholder text like '123 Fake St' or 'abcde' to seed fields during initial local runs. While this works for quick database writes, it bypasses real-world validation logic and fails to replicate the diversity of addresses global systems must handle." },
      { type: 'h2', text: 'Why Real Address Formats Matter' },
      { type: 'p', text: "Many developer teams make the mistake of using generic strings like 'Test Street 1' or '123 Fake St' during their manual testing phases. However, when these applications are deployed to staging or production, they interact with real-world validation APIs (such as Google Places or Loqate). Incomplete or structurally invalid addresses will cause forms to fail, leading to poor user experience." },
      { type: 'p', text: "For example, international address forms typically check for specific length requirements, regional character sets, and postcode syntax. In the UK, a postcode must match a strict alphanumeric pattern, while in Japan, the address structure is hierarchical, starting with the prefecture and moving to sub-districts and blocks. If your test suite doesn't utilize realistic mock addresses, these field rules can lead to undetected failures, blocked registration flows, or shipping errors in production." },
      { type: 'h2', text: 'Integrating Mock Data Into Your QA Checklist' },
      { type: 'p', text: "Using a specialized mock address generator allows teams to test edge cases without compromising real user privacy. High-quality mock data ensures that ZIP codes match states, phone area codes align with provinces, and name fields conform to local naming conventions. By integrating realistic address patterns into your QA checklists, you can discover validation errors early, verify input formatting, and ensure a seamless registration experience for global users." }
    ]
  },
  'demystifying-us-zip-code-formats': {
    title: 'Demystifying US ZIP Code Formats & Validation Rules',
    description: 'Explains the 5-digit and 9-digit ZIP code system, how states match ZIP codes, and how to validate them in code.',
    content: [
      { type: 'p', text: "The United States postal system uses the Zone Improvement Plan (ZIP) code to identify geographical regions for delivery. Originally introduced as a simple five-digit code in 1963, it was later expanded in 1983 to include an additional four digits, known as ZIP+4. For software developers designing forms or checkout pipelines, understanding how these codes work and how they relate to states is essential for preventing shipping and user registration bottlenecks." },
      { type: 'h2', text: 'How ZIP Codes Are Structured' },
      { type: 'p', text: "A standard US ZIP code contains 5 numbers, where the first digit represents a broad group of US states (e.g., '0' for New England, '9' for the West Coast). The next two digits identify a central post office or processing facility within that region, and the final two identify the local delivery area." },
      { type: 'p', text: "The ZIP+4 system adds a hyphen and four more digits to specify a precise delivery segment, such as a city block, an office building, or a specific high-volume mail receiver. Many automated validation systems check both formats to ensure maximum compatibility." },
      { type: 'h2', text: 'Best Practices for ZIP Code Validation' },
      { type: 'p', text: "When validating ZIP codes in code, many forms make the mistake of enforcing a strict 5-digit rule, which blocks users trying to input ZIP+4 codes. Alternatively, some forms accept any 5-digit string, which results in typos going unnoticed." },
      { type: 'p', text: "Best practices suggest using a flexible regex pattern like ^\\d{5}(-\\d{4})?$ to validate the format while keeping the field optional. Additionally, developers can cross-reference the ZIP code with a local database to auto-populate the state and city, reducing input effort and eliminating spelling mistakes." }
    ]
  },
  'how-to-design-forms-for-global-addresses': {
    title: 'How to Design Web Forms for Global Addresses',
    description: 'Design guidelines for international forms, avoiding state/province validation lockouts, handling postcode field order.',
    content: [
      { type: 'p', text: "Designing input forms for international addresses is a common challenge for web developers. Many digital products are designed with local assumptions, resulting in forms that enforce validation rules that only work for a single country. For instance, requiring a state abbreviation or a specific postal code format can completely block users from countries that do not use these systems, leading to high cart abandonment rates and poor user satisfaction." },
      { type: 'h2', text: 'Dynamic Fields and Localization Rules' },
      { type: 'p', text: "To build a truly international-friendly address form, the first rule is to make fields dynamic or highly flexible. The 'State' or 'Province' field should never be a mandatory select dropdown unless it is customized to the selected country. For countries without states, this field should either be hidden or made optional. Similarly, the 'ZIP code' field label should dynamically change to 'Postal Code' or 'Postcode' based on the country, and validation should adjust or be bypassed entirely if the target country does not use postal codes." },
      { type: 'h2', text: 'Logical Field Order' },
      { type: 'p', text: "Another key design decision is the order of input fields. While Western address forms typically start with the street address and end with the country, Eastern formats (such as in Japan or China) follow a top-down hierarchy starting with the country, followed by the prefecture or province, city, and then the street details." },
      { type: 'p', text: "Providing a country selector at the very top of the form allows the page to dynamically rearrange fields and display local validation rules, ensuring a frictionless experience for users worldwide." }
    ]
  },
  'database-seeding-best-practices': {
    title: 'Best Practices for Seeding Database Address Fields',
    description: 'Tips for generating mock seed data for customer and shipping address tables in relational databases.',
    content: [
      { type: 'p', text: "Database seeding is the process of populating a database with an initial set of data. During development and testing phases, developers need a robust set of mock addresses to seed customer records, order details, and shipping tables. Having realistic seed data is critical for validating search queries, testing index performance, and demonstrating application features to stakeholders. However, seeding database tables with bad mock data can hide bugs and lead to false confidence." },
      { type: 'h2', text: 'Maintaining Relational and Realism Integrity' },
      { type: 'p', text: "When designing seed scripts, it is important to maintain referential integrity and format consistency. For example, if your customer table has a foreign key to a country table, your seeded addresses must use valid country IDs and respect relational constraints. Furthermore, address fields should be split into logical columns (e.g., street_1, street_2, city, state, postal_code, country) rather than stored as a single concatenated string. This allows for granular queries, reports, and easier integration with tax calculation or shipping APIs later on." },
      { type: 'h2', text: 'Using Pre-validated Templates' },
      { type: 'p', text: "To generate high-quality seed data, developers can write scripts that combine random name data with structured address templates. By using pre-validated city-state-ZIP pairings, the database is populated with data that looks authentic and behaves correctly under search queries (such as filtering orders by state or postcode). Keeping seed scripts automated and version-controlled alongside your database schema ensures that all members of the development team are testing on the same baseline." }
    ]
  },
  'postal-formats-uk-japan-canada': {
    title: 'Understanding Postal Formats in UK, Japan, and Canada',
    description: 'Compares postcodes like UK SW1A 1AA, Canada K1A 0B1, and Japan 〒100-0001 to help developers write parser patterns.',
    content: [
      { type: 'p', text: "Postal codes around the world vary dramatically in length, structure, and character sets. While some countries use simple numeric digits, others employ complex alphanumeric combinations. For web developers and QA professionals, writing validators or parsers for these formats requires a solid understanding of each nation's unique conventions. Let's explore the postal code architectures of three distinct systems: the United Kingdom, Canada, and Japan." },
      { type: 'h2', text: 'United Kingdom Postcodes' },
      { type: 'p', text: "In the United Kingdom, postcodes are alphanumeric and range from six to eight characters (including a space). The postcode is split into an outward code (identifying the district) and an inward code (identifying the street or building). The format looks like SW1A 1AA or EC1A 1BB. Because of the variation in length and character placements, a robust regular expression is needed to parse them correctly, and developers should avoid simple character length limits." },
      { type: 'h2', text: 'Canada and Japan Postcodes' },
      { type: 'p', text: 'Canada uses a highly consistent six-character alphanumeric format structured as \'Letter-Number-Letter Number-Letter-Number\' (e.g., K1A 0B1). The first letter indicates the province or territory, and the space in the middle is standard.' },
      { type: 'p', text: "Japan, on the other hand, utilizes a simple seven-digit numeric code formatted with a hyphen after the third digit (e.g., 100-0001). The code is often prefixed with the postal mark symbol '〒'." },
      { type: 'p', text: "By understanding these regional formats, developers can build forms that automatically validate inputs, parse values, and offer a localized experience to global users." }
    ]
  }
}

export const blogContentZh: Record<string, LocalizedBlogPost> = {
  'importance-of-mock-address-data-in-qa': {
    title: 'Mock 地址数据在 QA 测试中的重要性',
    description: '了解为什么真实的 Mock 地址数据对于测试现代 Web 应用程序和避免表单验证漏洞至关重要。',
    content: [
      { type: 'p', text: "在构建涉及产品寄送、支付处理或用户注册的 Web 应用程序时，表单验证是最关键的用户触点之一。然而，它也往往是测试最不彻底的环节之一。开发团队在本地运行测试时，经常使用通用的占位符文本（如 '123 Fake St' 或 'abcde'）来填充地址字段。虽然这对于快速的数据库写入测试可行，但它绕过了真实的验证逻辑，无法复制全球系统必须处理的地址多样性。" },
      { type: 'h2', text: '为什么真实地址格式至关重要' },
      { type: 'p', text: "许多开发团队在手动测试阶段会犯使用通用字符串（如 'Test Street 1'）的错误。然而，当这些应用程序部署到测试或生产环境并与真实的地址校验 API（如 Google Places 或 Loqate）交互时，不完整或结构无效的地址会导致表单提交失败，从而严重损害用户体验。" },
      { type: 'p', text: "例如，国际地址表单通常具有特定的长度限制、区域字符集要求以及邮编格式规则。在英国，邮编必须匹配严格的字母数字模式；而在日本，地址结构是层级式的，从都道府县开始，一直细分到市区町村和番地。如果你的测试套件没有使用真实的模拟地址，这些隐藏的校验规则可能会导致生产环境中的提交失败、注册流程阻塞或寄送错误。" },
      { type: 'h2', text: '将模拟地址融入你的 QA 测试清单' },
      { type: 'p', text: "使用专业的随机地址生成器可以让测试团队在不泄露真实用户隐私的情况下，覆盖各种边缘用例。高质量的模拟数据能确保邮政编码与州/省匹配、电话区号与地区一致、姓名符合本地命名习惯。通过将真实的地址模式融入你的 QA 测试清单，你可以在开发早期发现验证逻辑漏洞，校验输入框格式化效果，并确保全球用户都能享受流畅的注册和结账体验。" }
    ]
  },
  'demystifying-us-zip-code-formats': {
    title: '揭秘美国邮编（ZIP Code）格式与校验规则',
    description: '详细解析美国 5 位与 9 位邮编系统、州与邮编的对应匹配关系，以及如何在代码中进行有效验证。',
    content: [
      { type: 'p', text: "美国邮政系统使用 ZIP 编码（Zone Improvement Plan）来标识邮件递送的地理区域。ZIP 编码最初于 1963 年作为简单的 5 位数字推出，随后在 1983 年进行了扩展，引入了额外的 4 位数字，即所谓的 ZIP+4 编码。对于设计结账流程或用户注册管道的软件开发者而言，理解这些编码的工作原理及其与各州的对应关系，对于防止寄送延误和注册卡顿至关重要。" },
      { type: 'h2', text: '美国邮政编码的结构设计' },
      { type: 'p', text: "标准的美国邮编由 5 位数字组成。第一位数字代表一组美国州（例如 '0' 代表新英格兰地区，'9' 代表西海岸地区）。接下来的两位数字代表该区域内的核心邮政分拣中心，最后两位则标示具体的本地投递区域。" },
      { type: 'p', text: "而 ZIP+4 系统则通过增加一个连字符和 4 位数字，精确定位到更小的递送单元，例如某个街区、一栋写字楼或某个大客户接收点。许多自动化系统都会同时校验这两种格式以确保最佳兼容性。" },
      { type: 'h2', text: '邮编校验的最佳编程实践' },
      { type: 'p', text: "在编写校验代码时，很多开发者会犯“强行限制只能输入 5 位数字”的错误，导致输入 ZIP+4 的用户被卡住。相反，有些表单则过于宽松，允许任意 5 位数字，使得拼写错误无法被检测。" },
      { type: 'p', text: "最佳实践是采用灵活的正规表达式 pattern 如 ^\\d{5}(-\\d{4})?$ 进行格式匹配，同时保持后 4 位可选。此外，还可以通过将邮编与本地轻量级数据库进行关联，自动填充省（州）和城市名称，从而减少用户的输入成本，彻底消除拼写错误。" }
    ]
  },
  'how-to-design-forms-for-global-addresses': {
    title: '如何设计面向全球用户的地址输入表单',
    description: '介绍国际化地址表单的设计准则，如何避免州/省硬校验导致的阻塞，以及各国的字段展示顺序。',
    content: [
      { type: 'p', text: "设计面向全球用户的地址输入表单是 Web 开发者经常面临的挑战。许多数字化产品往往在本地化假设下设计，导致其表单强行套用只适用于单一国家的规则。例如，要求必须提供两个字符的“州/省”缩写或特定格式的邮编。这种硬性校验会完全阻塞那些不使用该系统的国家的用户，直接导致购物车放弃率攀升，严重损害用户满意度。" },
      { type: 'h2', text: '动态字段与本地化校验' },
      { type: 'p', text: "要构建一个真正对国际化友好的地址表单，首要原则是让字段保持动态和高灵活性。除非已根据用户所选国家进行了适配，否则“州/省”字段绝不应该是必选的下拉框。对于没有省份概念的国家，该字段应被隐藏或设为可选。同样，“邮编”字段的标签也应根据国家动态切换为 'Postal Code' 或 'Postcode'，如果目标国家不使用邮编系统（如阿联酋或巴拿马），则应自动放宽校验或完全免检。" },
      { type: 'h2', text: '合理的字段排列顺序' },
      { type: 'p', text: "另一个关键的设计决策是输入框的排列顺序。西方地址表单通常以街道地址开头，以国家结尾；而东方格式（如日本或中国）则遵循自上而下的层级结构，从国家开始，随后是省/都道府县、城市，最后是详细街道信息。" },
      { type: 'p', text: "在表单顶部提供“国家选择器”是业内公认的标准做法，这允许页面动态重组字段顺序并实时切换本地化的校验规则，为全球用户提供无摩擦的输入体验。" }
    ]
  },
  'database-seeding-best-practices': {
    title: '数据库地址字段 Seeding 的最佳实践',
    description: '介绍如何为关系型数据库中的客户和发货地址表生成高质量、具有逻辑关联性的模拟种子数据。',
    content: [
      { type: 'p', text: "数据库 Seeding 是指向数据库中填充初始数据集的过程。在开发和测试阶段，开发者需要一套扎实的模拟地址数据来填充客户记录、订单详情和物流表。拥有逼真的种子数据对于验证搜索查询的准确性、测试数据库索引性能以及向利益相关者演示应用功能至关重要。然而，使用低质量或完全随机的杂乱数据进行填充，往往会掩盖潜在的 Bug 并带来虚假的安全感。" },
      { type: 'h2', text: '保持关系完整性与数据的合理关联' },
      { type: 'p', text: "在编写 Seeding 脚本时，保持关联关系的一致性和格式的合理性非常重要。例如，如果你的客户表包含指向国家表的外键，那么你生成的模拟地址必须使用合规的国家 ID 并遵循相应的关系约束。此外，地址字段应当被合理拆分存入单独的列（如 street_1, street_2, city, state, postal_code, country），而不是作为一整行文本揉在一起。这有助于后期进行细粒度的过滤查询、财务报表统计，以及与第三方计税或物流 API 顺利对接。" },
      { type: 'h2', text: '使用预验证的模板生成种子' },
      { type: 'p', text: "要生成高质量的种子数据，开发者可以编写脚本，将随机人名与结构化的地址模板进行组合。通过使用经过校验的“城市-州-邮编”真实组合，数据库中的数据在执行搜索查询（如按州统计订单）时将表现得非常自然真实。将数据生成脚本自动化，并与数据库 schema 版本同步管理，能确保开发团队的每个成员都在相同的测试基准线上协作。" }
    ]
  },
  'postal-formats-uk-japan-canada': {
    title: '深入理解英国、日本与加拿大的邮政地址格式',
    description: '对比英国 SW1A 1AA、加拿大 K1A 0B1 和日本 7 位邮编，帮助开发者编写健壮的地址解析逻辑。',
    content: [
      { type: 'p', text: "全球各国的邮政编码在长度、字符类型和内部结构上存在巨大差异。有些国家使用纯数字，而另一些国家则使用复杂的字母与数字组合。对于 Web 开发者和 QA 工程师来说，编写健壮的校验器或解析器需要深入理解各国的规则。本文将带你探索英国、加拿大和日本这三个极具代表性的邮政编码架构。" },
      { type: 'h2', text: '英国的字母数字邮编（Postcode）' },
      { type: 'p', text: "英国的邮编是由字母和数字组成的，长度在 6 到 8 个字符之间（含空格）。邮编通常被拆分为外向编码（outward code，标识行政区）和内向编码（inward code，指明具体街道或建筑），例如 SW1A 1AA 或 EC1A 1BB。由于长度和字母排列多变，开发者必须使用完备的正则表达式来匹配，避免设置死板的字符长度限制。" },
      { type: 'h2', text: '加拿大与日本的邮编结构' },
      { type: 'p', text: "加拿大采用非常规范的 6 位字母数字格式，结构为“字母-数字-字母 空格 数字-字母-数字”（例如 K1A 0B1）。其中第一个字母代表省份或地区，中间的空格是标准格式的一部分。" },
      { type: 'p', text: "日本则使用简单的 7 位纯数字编码，在第 3 位数字后带有一个连字符（如 100-0001）。在日本网页中，邮编前经常会带有特有的邮政符号 '〒'。" },
      { type: 'p', text: "理解这些区域格式并利用生成器输出进行充分的测试，有助于开发者编写出适应全球化需求的表单，自动解析字段，为世界各地的用户提供卓越的本地化体验。" }
    ]
  }
}
