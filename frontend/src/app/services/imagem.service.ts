import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ImagemService {

  constructor() { }

  toBase64(image: any) {

    if (image) {

      return new Promise<any>((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
      });
    }

    return null;
  }
}