FROM node:22-alpine AS build

WORKDIR /app

ARG NUXT_PUBLIC_API_BASE_URL=http://backend:8080
ARG NUXT_PUBLIC_SITE_URL=https://globaladdresshub.com
ENV NUXT_PUBLIC_API_BASE_URL=${NUXT_PUBLIC_API_BASE_URL}
ENV NUXT_PUBLIC_SITE_URL=${NUXT_PUBLIC_SITE_URL}

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM node:22-alpine AS runtime

WORKDIR /app
ENV NODE_ENV=production
ENV HOST=0.0.0.0
ENV PORT=3000

COPY --from=build /app/.output ./.output

EXPOSE 3000
CMD ["node", ".output/server/index.mjs"]
