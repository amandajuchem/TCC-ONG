CREATE TABLE tb_adocoes
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    local              VARCHAR(10),
    local_adocao       VARCHAR(10),
    vale_castracao     BOOLEAN                     NOT NULL,
    animal_id          UUID,
    tutor_id           UUID,
    CONSTRAINT pk_tb_adocoes PRIMARY KEY (id)
);

CREATE TABLE tb_agendamentos
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    descricao          TEXT,
    animal_id          UUID,
    veterinario_id     UUID,
    CONSTRAINT pk_tb_agendamentos PRIMARY KEY (id)
);

CREATE TABLE tb_animais
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(50),
    idade              INTEGER     NOT NULL,
    especie            VARCHAR(10) NOT NULL,
    sexo               VARCHAR(5)  NOT NULL,
    raca               VARCHAR(50),
    cor                VARCHAR(50),
    porte              VARCHAR(10) NOT NULL,
    situacao           VARCHAR(10) NOT NULL,
    CONSTRAINT pk_tb_animais PRIMARY KEY (id)
);

CREATE TABLE tb_atendimentos
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    motivo             VARCHAR(25)                 NOT NULL,
    diagnostico        TEXT,
    posologia          TEXT,
    animal_id          UUID,
    veterinario_id     UUID,
    CONSTRAINT pk_tb_atendimentos PRIMARY KEY (id)
);

CREATE TABLE tb_atendimentos_exames
(
    exames_id          UUID NOT NULL,
    tb_atendimentos_id UUID NOT NULL,
    CONSTRAINT pk_tb_atendimentos_exames PRIMARY KEY (exames_id, tb_atendimentos_id)
);

CREATE TABLE tb_enderecos
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    rua                VARCHAR(100),
    numero_residencia  VARCHAR(10) NOT NULL,
    bairro             VARCHAR(50),
    cidade             VARCHAR(100),
    estado             VARCHAR(25) NOT NULL,
    complemento        VARCHAR(100),
    cep                VARCHAR(8),
    tutor_id           UUID,
    CONSTRAINT pk_tb_enderecos PRIMARY KEY (id)
);

CREATE TABLE tb_exames
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    categoria          VARCHAR(25),
    CONSTRAINT pk_tb_exames PRIMARY KEY (id)
);

CREATE TABLE tb_feiras_adocao
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_feiras_adocao PRIMARY KEY (id)
);

CREATE TABLE tb_feiras_adocao_animais
(
    animais_id          UUID NOT NULL,
    tb_feiras_adocao_id UUID NOT NULL,
    CONSTRAINT pk_tb_feiras_adocao_animais PRIMARY KEY (animais_id, tb_feiras_adocao_id)
);

CREATE TABLE tb_feiras_adocao_usuarios
(
    tb_feiras_adocao_id UUID NOT NULL,
    usuarios_id         UUID NOT NULL,
    CONSTRAINT pk_tb_feiras_adocao_usuarios PRIMARY KEY (tb_feiras_adocao_id, usuarios_id)
);

CREATE TABLE tb_fichas_medicas
(
    id                 UUID    NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    comorbidades       TEXT,
    castrado           BOOLEAN NOT NULL,
    animal_id          UUID,
    CONSTRAINT pk_tb_fichas_medicas PRIMARY KEY (id)
);

CREATE TABLE tb_imagens
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(25),
    adocao_id          UUID,
    animal_id          UUID,
    atendimento_id     UUID,
    tutor_id           UUID,
    usuario_id         UUID,
    CONSTRAINT pk_tb_imagens PRIMARY KEY (id)
);

CREATE TABLE tb_observacoes
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    conteudo           TEXT,
    tutor_id           UUID,
    CONSTRAINT pk_tb_observacoes PRIMARY KEY (id)
);

CREATE TABLE tb_telefones
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    numero             VARCHAR(11),
    tutor_id           UUID,
    CONSTRAINT pk_tb_telefones PRIMARY KEY (id)
);

CREATE TABLE tb_tutores
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    cpf                VARCHAR(11),
    rg                 VARCHAR(13),
    situacao           VARCHAR(10) NOT NULL,
    CONSTRAINT pk_tb_tutores PRIMARY KEY (id)
);

CREATE TABLE tb_usuarios
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    cpf                VARCHAR(11),
    senha              VARCHAR(255),
    status             BOOLEAN     NOT NULL,
    setor              VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tb_usuarios PRIMARY KEY (id)
);

ALTER TABLE tb_atendimentos_exames
    ADD CONSTRAINT uc_tb_atendimentos_exames_exames UNIQUE (exames_id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT uc_tb_feiras_adocao_animais_animais UNIQUE (animais_id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT uc_tb_feiras_adocao_usuarios_usuarios UNIQUE (usuarios_id);

ALTER TABLE tb_adocoes
    ADD CONSTRAINT FK_TB_ADOCOES_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_adocoes
    ADD CONSTRAINT FK_TB_ADOCOES_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_agendamentos
    ADD CONSTRAINT FK_TB_AGENDAMENTOS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_agendamentos
    ADD CONSTRAINT FK_TB_AGENDAMENTOS_ON_VETERINARIO FOREIGN KEY (veterinario_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_atendimentos
    ADD CONSTRAINT FK_TB_ATENDIMENTOS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_atendimentos
    ADD CONSTRAINT FK_TB_ATENDIMENTOS_ON_VETERINARIO FOREIGN KEY (veterinario_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_enderecos
    ADD CONSTRAINT FK_TB_ENDERECOS_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_fichas_medicas
    ADD CONSTRAINT FK_TB_FICHAS_MEDICAS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_imagens
    ADD CONSTRAINT FK_TB_IMAGENS_ON_ADOCAO FOREIGN KEY (adocao_id) REFERENCES tb_adocoes (id);

ALTER TABLE tb_imagens
    ADD CONSTRAINT FK_TB_IMAGENS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_imagens
    ADD CONSTRAINT FK_TB_IMAGENS_ON_ATENDIMENTO FOREIGN KEY (atendimento_id) REFERENCES tb_atendimentos (id);

ALTER TABLE tb_imagens
    ADD CONSTRAINT FK_TB_IMAGENS_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_imagens
    ADD CONSTRAINT FK_TB_IMAGENS_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_observacoes
    ADD CONSTRAINT FK_TB_OBSERVACOES_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_telefones
    ADD CONSTRAINT FK_TB_TELEFONES_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_atendimentos_exames
    ADD CONSTRAINT fk_tbateexa_on_atendimento FOREIGN KEY (tb_atendimentos_id) REFERENCES tb_atendimentos (id);

ALTER TABLE tb_atendimentos_exames
    ADD CONSTRAINT fk_tbateexa_on_exame FOREIGN KEY (exames_id) REFERENCES tb_exames (id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT fk_tbfeiadoani_on_animal FOREIGN KEY (animais_id) REFERENCES tb_animais (id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT fk_tbfeiadoani_on_feira_adocao FOREIGN KEY (tb_feiras_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT fk_tbfeiadousu_on_feira_adocao FOREIGN KEY (tb_feiras_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT fk_tbfeiadousu_on_usuario FOREIGN KEY (usuarios_id) REFERENCES tb_usuarios (id);