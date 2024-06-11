
FROM node:latest

WORKDIR /app

COPY package.json ./

# Installiere Abhängigkeiten
RUN npm install


COPY . .


RUN npm run build

EXPOSE 80

CMD ["npm","run" , "dev"]