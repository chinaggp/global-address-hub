import type { CountryOption, CountryPageContent } from '~/types/address'

export const countryOptions: CountryOption[] = [
  { code: 'US', name: 'United States', slug: '/us-address-generator' },
  { code: 'JP', name: 'Japan', slug: '/japan-address-generator' },
  { code: 'UK', name: 'United Kingdom', slug: '/uk-address-generator' },
  { code: 'CA', name: 'Canada', slug: '/canada-address-generator' },
  { code: 'AU', name: 'Australia', slug: '/australia-address-generator' },
  { code: 'TR', name: 'Turkey', slug: '/turkey-address-generator' },
  { code: 'NG', name: 'Nigeria', slug: '/nigeria-address-generator' }
]

export const countryPages: Record<string, CountryPageContent> = {
  US: {
    code: 'US',
    name: 'United States',
    slug: '/us-address-generator',
    title: 'Random US Address Generator - Free United States Address Tool',
    description:
      'Generate random United States addresses with ZIP code, city, state, and phone number for software testing, QA, and form validation.',
    h1: 'Random US Address Generator',
    heroCopy:
      'Generate United States address sample data with state, city, ZIP code, and phone formatting for software testing, form testing, and educational use only.',
    regionLabel: 'State',
    faq: [
      {
        question: 'What is a US address format?',
        answer:
          'A US address usually includes a street line, city, state abbreviation, ZIP code, country, and sometimes a phone field for test form coverage.'
      },
      {
        question: 'Can I generate an address for a specific state?',
        answer:
          'Yes. Select a state in the region field when the backend provides state options for the United States.'
      },
      {
        question: 'Are these generated US addresses real?',
        answer:
          'The output is sample data shaped like US address fields. It is provided for testing and educational use only.'
      },
      {
        question: 'Can I use this for software testing?',
        answer:
          'Yes. The generated fields are intended for software testing, form testing, QA checks, demos, and educational examples.'
      }
    ],
    seoTitle: 'US address fields for test forms',
    seoBody: [
      'United States forms commonly ask for street address, city, state, ZIP code, country, and phone fields. GlobalAddressHub keeps those fields visible so QA teams can inspect layout, validation, and copy behavior.',
      'Use the generated sample as disposable test data in frontend testing, database seed data, demo content, and educational scenarios.'
    ]
  },
  JP: {
    code: 'JP',
    name: 'Japan',
    slug: '/japan-address-generator',
    title: 'Random Japan Address Generator - Free Japanese Address Tool',
    description:
      'Generate random Japanese addresses with postal code, prefecture, city, street, and phone number formatting for testing and form validation.',
    h1: 'Random Japan Address Generator',
    heroCopy:
      'Create Japanese address samples with postal code, prefecture, city, and local address fields for QA, form testing, and educational use only.',
    regionLabel: 'Prefecture',
    faq: [
      {
        question: 'What is a Japanese address format?',
        answer:
          'A Japanese address commonly includes postal code, prefecture, city or ward, local street details, country, and phone formatting.'
      },
      {
        question: 'Does the generator include Japanese postal codes?',
        answer:
          'Yes. The address result includes a postal code field when the backend response provides it.'
      },
      {
        question: 'Are generated Japan addresses real?',
        answer:
          'The generated result is address sample data for testing and educational use only, not a verified location record.'
      },
      {
        question: 'Can I copy the generated address for test data?',
        answer:
          'Yes. Use Copy All or copy individual lines to move fields into a test form or QA note.'
      }
    ],
    seoTitle: 'Japan address fields for QA workflows',
    seoBody: [
      'Japanese address forms often require a postal code, prefecture, municipality, street details, and phone fields. This page helps testers review those field groups quickly.',
      'The generated content is intended for software testing, form testing, demo content, and educational walkthroughs.'
    ]
  },
  UK: {
    code: 'UK',
    name: 'United Kingdom',
    slug: '/uk-address-generator',
    title: 'Random UK Address Generator - Free United Kingdom Address Tool',
    description:
      'Generate random United Kingdom addresses with postcode, town, county, and phone number formatting for testing, QA, and form validation.',
    h1: 'Random UK Address Generator',
    heroCopy:
      'Generate UK-style address data with postcode formatting, town, street, and phone fields for software testing and educational use only.',
    regionLabel: 'Area',
    faq: [
      {
        question: 'What is a UK postcode format?',
        answer:
          'A UK postcode combines outward and inward parts. Test forms often place it near town, county, street, and country fields.'
      },
      {
        question: 'Does this tool generate UK regional style data?',
        answer:
          'Yes. Select an available region when the backend provides regional options for UK address samples.'
      },
      {
        question: 'Are generated UK addresses real?',
        answer:
          'The output is generated sample data for testing and educational use only, not a confirmed location record.'
      },
      {
        question: 'Can I use these addresses in test forms?',
        answer:
          'Yes. They are intended for software testing, form testing, validation checks, and demos.'
      }
    ],
    seoTitle: 'UK address format notes',
    seoBody: [
      'United Kingdom address forms often include street, town, county, postcode, country, and phone fields. A format-aware sample helps teams check spacing, required fields, and copy behavior.',
      'GlobalAddressHub keeps the page focused on QA, software testing, form validation, and educational use only.'
    ]
  },
  CA: {
    code: 'CA',
    name: 'Canada',
    slug: '/canada-address-generator',
    title: 'Random Canada Address Generator - Free Canadian Address Tool',
    description:
      'Generate random Canadian addresses with province, city, postal code, and phone number formatting for software testing and form validation.',
    h1: 'Random Canada Address Generator',
    heroCopy:
      'Create Canadian address samples with province, city, postal code, street, and phone fields for test data and educational use only.',
    regionLabel: 'Province',
    faq: [
      {
        question: 'What is a Canadian postal code format?',
        answer:
          'A Canadian postal code uses alternating letters and numbers. It is commonly shown with city, province, street, and country fields.'
      },
      {
        question: 'Can I generate an address by province?',
        answer:
          'Yes. Choose a province in the region selector when province options are returned by the backend.'
      },
      {
        question: 'Are generated Canadian addresses real?',
        answer:
          'The data is generated for software testing and educational use only. It should be treated as sample data.'
      },
      {
        question: 'Can I copy Canadian address data in one click?',
        answer:
          'Yes. Use Copy All to copy the generated fields together, or copy the formatted address line.'
      }
    ],
    seoTitle: 'Canada address samples for testing',
    seoBody: [
      'Canadian test forms usually include street, city, province, postal code, country, and phone fields. This page keeps those values grouped for quick validation checks.',
      'Generated Canadian samples are useful for frontend testing, QA notes, seed data, and educational demos.'
    ]
  },
  AU: {
    code: 'AU',
    name: 'Australia',
    slug: '/australia-address-generator',
    title: 'Random Australia Address Generator - Free Australian Address Tool',
    description:
      'Generate random Australian addresses with state, suburb, postcode, and phone number formatting for QA, software testing, and form validation.',
    h1: 'Random Australia Address Generator',
    heroCopy:
      'Generate Australian address data with suburb, state, postcode, street, and phone formatting for testing workflows and educational use only.',
    regionLabel: 'State',
    faq: [
      {
        question: 'What is an Australian address format?',
        answer:
          'An Australian address commonly includes street, suburb, state abbreviation, postcode, country, and phone fields.'
      },
      {
        question: 'Does this generator include Australian postcodes?',
        answer:
          'Yes. The postal code field appears when the backend returns it for the generated sample.'
      },
      {
        question: 'Are generated Australian addresses real?',
        answer:
          'The generated values are sample data for testing and educational use only.'
      },
      {
        question: 'Can I use this for QA and form testing?',
        answer:
          'Yes. It is designed for software testing, form testing, QA review, demo data, and educational examples.'
      }
    ],
    seoTitle: 'Australia address fields for form testing',
    seoBody: [
      'Australian address forms often ask for street, suburb, state, postcode, country, and phone details. Sample data helps verify form layout and field handling.',
      'This page supports testing workflows and educational demos without adding unrelated user systems.'
    ]
  },
  TR: {
    code: 'TR',
    name: 'Turkey',
    slug: '/turkey-address-generator',
    title: 'Random Turkey Address Generator - Free Turkish Address Tool',
    description:
      'Generate random Turkey addresses with province, district, postal code, street, and phone number formatting for software testing and QA.',
    h1: 'Random Turkey Address Generator',
    heroCopy:
      'Create Turkey address samples with province, district, postal code, street, and phone fields for test forms and educational use only.',
    regionLabel: 'Province',
    faq: [
      {
        question: 'What is a Turkey address format?',
        answer:
          'A Turkey address sample can include province, district, street, postal code, country, and phone fields for test form coverage.'
      },
      {
        question: 'Does this tool include Turkish provinces and districts?',
        answer:
          'The region selector can show province options when the backend provides them for Turkey.'
      },
      {
        question: 'Are generated Turkey addresses real?',
        answer:
          'The generated address is sample data for software testing and educational use only.'
      },
      {
        question: 'Can I copy Turkish address data for software testing?',
        answer:
          'Yes. Copy buttons are available for the full generated result and formatted address line.'
      }
    ],
    seoTitle: 'Turkey address sample fields',
    seoBody: [
      'Turkey address forms may include province, district, street, postal code, country, and phone fields. Generated samples are useful for checking test form behavior.',
      'Use this page for QA, software testing, form validation, and educational demos.'
    ]
  },
  NG: {
    code: 'NG',
    name: 'Nigeria',
    slug: '/nigeria-address-generator',
    title: 'Random Nigeria Address Generator - Free Nigerian Address Tool',
    description:
      'Generate random Nigerian addresses with state, city, postal code, street, and phone number formatting for software testing and validation.',
    h1: 'Random Nigeria Address Generator',
    heroCopy:
      'Generate Nigerian address samples with state, city or local government area, postal code, street, and phone fields for QA and educational use only.',
    regionLabel: 'State',
    faq: [
      {
        question: 'What is a Nigerian address format?',
        answer:
          'A Nigerian address sample can include street, city or local government area, state, postal code, country, and phone fields.'
      },
      {
        question: 'Does this generator include Nigerian states?',
        answer:
          'Yes. The region selector can list state options when the backend returns them.'
      },
      {
        question: 'Are generated Nigerian addresses real?',
        answer:
          'The output is generated sample data for testing and educational use only.'
      },
      {
        question: 'Can I use Nigerian address samples for test data?',
        answer:
          'Yes. Use them for software testing, form testing, QA checks, seed data, and educational demos.'
      }
    ],
    seoTitle: 'Nigeria address data for test forms',
    seoBody: [
      'Nigerian address forms may collect street, city or local government area, state, postal code, country, and phone fields. Sample data helps testers exercise each field.',
      'GlobalAddressHub provides generated Nigerian samples for software testing, form testing, and educational use only.'
    ]
  }
}

export const countryPagesBySlug = Object.fromEntries(
  Object.values(countryPages).map((page) => [page.slug, page])
)
