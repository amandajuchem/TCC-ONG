import { AbstractEntity } from './abstract-entity';
import { Animal } from './animal';
import { Usuario } from './usuario';

export interface FeiraAdocao extends AbstractEntity {
    nome: string;
    dataHora: Date;
    animais: Array<Animal>;
    usuarios: Array<Usuario>;
}