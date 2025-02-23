export abstract class FileUtils {

    static getExtension(file: any) {

        let filenameArray: Array<string> = []; 

        if (typeof file === 'string') {
            filenameArray = file.split('.');
        }

        if (typeof file === 'object') {
            filenameArray = file.name.split('.');
        }

        const extension = filenameArray[filenameArray.length - 1];
        return extension;
    }

    static isImage(file: any) {
        const imageExtensions = ['jpeg', 'jpg', 'png', 'gif', 'bmp', 'tiff', 'svg+xml', 'webp', 'heif'];
        const extension = this.getExtension(file);
        return imageExtensions.includes(extension);
    }

    static isPDF(file: any) {
        const extension = this.getExtension(file);
        return extension === 'pdf';
    }
}