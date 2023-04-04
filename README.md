# Projeto API

Este projeto foi construido utilizando Java 17 + Spring Boot 2.7.10 no backend e TypeScript + Angular 15

## Instalação do backend no Docker

Utilize o Maven para fazer o build do backend

Crie a rede do projeto

<pre><code>docker network create projeto</code></pre>

Faça o download da imagem do PostgreSQL

<pre><code>docker pull postgres</code></pre>

Execute a imagem do PostgreSQL

<pre><code>docker run -p 5432:5432 --name projeto-database --network projeto -e POSTGRES_PASSWORD=projeto -e POSTGRES_USER=projeto -e POSTGRES_DB=projeto -d postgres</code></pre>

Crie a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da compilação, por exemplo, por 1.0.0; 

<pre><code>docker build -t projeto-backend:versão* .</code></pre>

Execute a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da imagem, por exemplo, por 1.0.0;  

<pre><code>docker run -p 8080:8080 --name projeto-backend --network projeto -e APP_DATASOURCE_URL=jdbc:postgresql://projeto-database:5432/projeto -e APP_DATASOURCE_USERNAME=projeto -e APP_DATASOURCE_PASSWORD=projeto -d projeto-backend:versão</code></pre>

## Instalação do frontend no Docker

Crie a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da compilação, por exemplo, por 1.0.0; 

<pre><code>docker build -t projeto-frontend:versão .</code></pre>

Execute a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da imagem, por exemplo, por 1.0.0;  

<pre><code>docker run -p 80:80 --name projeto-frontend --network projeto -d projeto-frontend:versão</code></pre>