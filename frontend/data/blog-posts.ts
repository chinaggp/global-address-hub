export interface BlogPostMetadata {
  slug: string
  date: string
  author: string
  tag: string
  readTime: string
}

export const blogPosts: BlogPostMetadata[] = [
  {
    slug: 'importance-of-mock-address-data-in-qa',
    date: '2026-05-20',
    author: 'Alex Carter',
    tag: 'QA Testing',
    readTime: '5 min read'
  },
  {
    slug: 'demystifying-us-zip-code-formats',
    date: '2026-05-24',
    author: 'Sarah Chen',
    tag: 'Validation',
    readTime: '6 min read'
  },
  {
    slug: 'how-to-design-forms-for-global-addresses',
    date: '2026-05-28',
    author: 'Elena Rostova',
    tag: 'UX Design',
    readTime: '8 min read'
  },
  {
    slug: 'database-seeding-best-practices',
    date: '2026-06-01',
    author: 'Marcus Vance',
    tag: 'Database',
    readTime: '5 min read'
  },
  {
    slug: 'postal-formats-uk-japan-canada',
    date: '2026-06-02',
    author: 'Kenji Sato',
    tag: 'Localization',
    readTime: '7 min read'
  }
]
