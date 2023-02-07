import { AbstractEntity } from "./abstract-entity";
import { Animal } from "./animal";
import { Usuario } from "./usuario";

export interface Agendamento extends AbstractEntity {
    dataHora: Date;
    descricao: string;
    animal: Animal;
    veterinario: Usuario;
}