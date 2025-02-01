FROM node:20

WORKDIR /software-system-frontend

COPY package*.json ./
RUN npm install -g npm@latest
RUN npm install -g @angular/cli@latest

# Install app dependencies
RUN npm install --legacy-peer-deps

# Copy the rest of the application code to the container
COPY . .

# Build the application
RUN npm run build
EXPOSE 4200

# Start the application using Angular CLI
ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0", "--disable-host-check"]
