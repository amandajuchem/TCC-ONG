CREATE TABLE tb_adocoes
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    vale_castracao     BOOLEAN                     NOT NULL,
    animal_id          UUID,
    CONSTRAINT pk_tb_adocoes PRIMARY KEY (id)
);

CREATE TABLE tb_animais
(
    id                 UUID         NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    nome               VARCHAR(255),
    idade              INTEGER      NOT NULL,
    especie            VARCHAR(255),
    local              VARCHAR(255),
    local_adocao       VARCHAR(255),
    sexo               VARCHAR(255) NOT NULL,
    raca               VARCHAR(255),
    data_adocao        date,
    data_resgate       date,
    cor                VARCHAR(255),
    porte              VARCHAR(255) NOT NULL,
    castrado           BOOLEAN      NOT NULL,
    situacao           VARCHAR(255) NOT NULL,
    tutor_id           UUID,
    CONSTRAINT pk_tb_animais PRIMARY KEY (id)
);

CREATE TABLE tb_atendimentos
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_hora_retorno  TIMESTAMP WITHOUT TIME ZONE,
    motivo             VARCHAR(255),
    diagnostico        TEXT,
    exames             VARCHAR(255),
    procedimentos      VARCHAR(255),
    posologia          VARCHAR(255),
    animal_id          UUID,
    veterinario_id     UUID,
    CONSTRAINT pk_tb_atendimentos PRIMARY KEY (id)
);

CREATE TABLE tb_castracoes
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    animal_id          UUID,
    usuario_id         UUID,
    CONSTRAINT pk_tb_castracoes PRIMARY KEY (id)
);

CREATE TABLE tb_enderecos
(
    id                 UUID         NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    rua                VARCHAR(255),
    numero_residencia  VARCHAR(255) NOT NULL,
    bairro             VARCHAR(255),
    cidade             VARCHAR(255),
    estado             VARCHAR(255) NOT NULL,
    complemento        VARCHAR(255),
    cep                VARCHAR(8),
    CONSTRAINT pk_tb_enderecos PRIMARY KEY (id)
);

CREATE TABLE tb_feiras_adocao
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    nome               VARCHAR(255),
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
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    comorbidades       TEXT,
    animal_id          UUID,
    CONSTRAINT pk_tb_fichas_medicas PRIMARY KEY (id)
);

CREATE TABLE tb_imagens
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    nome               VARCHAR(255),
    adocao_id          UUID,
    animal_id          UUID,
    atendimento_id     UUID,
    tutor_id           UUID,
    usuario_id         UUID,
    CONSTRAINT pk_tb_imagens PRIMARY KEY (id)
);

CREATE TABLE tb_tutores
(
    id                 UUID         NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    nome               VARCHAR(255),
    cpf                VARCHAR(11),
    rg                 VARCHAR(13),
    telefone           VARCHAR(11),
    situacao           VARCHAR(255) NOT NULL,
    observacao         TEXT,
    endereco_id        UUID,
    CONSTRAINT pk_tb_tutores PRIMARY KEY (id)
);

CREATE TABLE tb_usuarios
(
    id                 UUID         NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(255),
    modified_by_user   VARCHAR(255),
    nome               VARCHAR(255),
    cpf                VARCHAR(11),
    senha              VARCHAR(255),
    status             BOOLEAN      NOT NULL,
    setor              VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tb_usuarios PRIMARY KEY (id)
);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT uc_tb_feiras_adocao_animais_animais UNIQUE (animais_id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT uc_tb_feiras_adocao_usuarios_usuarios UNIQUE (usuarios_id);

ALTER TABLE tb_adocoes
    ADD CONSTRAINT FK_TB_ADOCOES_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_animais
    ADD CONSTRAINT FK_TB_ANIMAIS_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_atendimentos
    ADD CONSTRAINT FK_TB_ATENDIMENTOS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_atendimentos
    ADD CONSTRAINT FK_TB_ATENDIMENTOS_ON_VETERINARIO FOREIGN KEY (veterinario_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_castracoes
    ADD CONSTRAINT FK_TB_CASTRACOES_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_castracoes
    ADD CONSTRAINT FK_TB_CASTRACOES_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES tb_usuarios (id);

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

ALTER TABLE tb_tutores
    ADD CONSTRAINT FK_TB_TUTORES_ON_ENDERECO FOREIGN KEY (endereco_id) REFERENCES tb_enderecos (id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT fk_tbfeiadoani_on_animal FOREIGN KEY (animais_id) REFERENCES tb_animais (id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT fk_tbfeiadoani_on_feira_adocao FOREIGN KEY (tb_feiras_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT fk_tbfeiadousu_on_feira_adocao FOREIGN KEY (tb_feiras_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT fk_tbfeiadousu_on_usuario FOREIGN KEY (usuarios_id) REFERENCES tb_usuarios (id);