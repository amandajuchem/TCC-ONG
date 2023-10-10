import { AbstractEntity } from "./abstract-entity";
import { Exame } from "./exame";
import { Arquivo } from "./imagem";

export interface ExameRealizado extends AbstractEntity {
    exame: Exame;
    arquivo: Arquivo;
}