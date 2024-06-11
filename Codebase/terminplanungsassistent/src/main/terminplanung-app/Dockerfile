
FROM node:latest

WORKDIR /app

COPY package*.json ./

# Installiere Abhängigkeiten
RUN npm install


COPY . .


RUN npm run dev

EXPOSE 80


CMD ["npm", "start"]