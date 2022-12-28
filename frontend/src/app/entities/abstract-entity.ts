export interface AbstractEntity {
    id: string;
    createdDate: Date;
    lastModifiedDate: Date;
    createdByUser: string;
    modifiedByUser: string;
}