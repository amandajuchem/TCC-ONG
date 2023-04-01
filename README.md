# Projeto API

Este projeto foi construido utilizando Java 17 + Spring Boot 2.7.6 no backend e TypeScript + Angular 15

## Instalação do backend no Docker

Utilize o Maven para fazer o build do backend

Crie a rede do projeto

<pre><code>docker network create projeto-api</code></pre>

Faça o download da imagem do PostgreSQL

<pre><code>docker pull postgres</code></pre>

Execute a imagem do PostgreSQL

<pre><code>docker run -p 5432:5432 --name projeto-api-database --network projeto-api -e POSTGRES_PASSWORD=projeto -e POSTGRES_USER=projeto -e POSTGRES_DB=projeto -d postgres</code></pre>

Crie a imagem do projeto

<pre><code>docker build -t projeto-api-backend:latest .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 8080:8080 --name projeto-api-backend --network projeto-api -e APP_DATASOURCE_URL=jdbc:postgresql://projeto-api-database:5432/projeto -e APP_DATASOURCE_USERNAME=projeto -e APP_DATASOURCE_PASSWORD=projeto -d projeto-api-backend:latest</code></pre>

## Instalação do frontend no Docker

Crie a imagem do projeto

<pre><code>docker build -t projeto-api-frontend:latest .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 80:80 --network projeto-api -d projeto-api-frontend:latest</code></pre>