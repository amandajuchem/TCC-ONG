import { AbstractEntity } from "./abstract-entity";
import { Exame } from "./exame";
import { Imagem } from "./imagem";

export interface ExameRealizado extends AbstractEntity {
    exame: Exame;
    imagem: Imagem;
}