FROM node:20.17.0

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json /app/package.json

RUN npm install -g @angular/cli@16.2.15

RUN npm install

CMD ["ng", "serve", "--host", "0.0.0.0"]