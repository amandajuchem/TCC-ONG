# Projeto API

Este projeto foi construido utilizando Java 17 + Spring Boot 3.1.0 no backend e TypeScript + Angular 15

## Rede do projeto

<pre><code>docker network create projeto</code></pre>

## Instalação do backend no Docker

Utilize o Maven para fazer o build do backend

Crie a imagem do projeto

<pre><code>docker build -t projeto-backend .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 8080:8080 --name projeto-backend --network projeto -d projeto-backend</code></pre>

## Instalação do frontend no Docker

Crie a imagem do projeto

<pre><code>docker build -t projeto-frontend .</code></pre>

Execute a imagem do projeto

<pre><code>docker run -p 80:80 --name projeto-frontend --network projeto -d projeto-frontend</code></pre>