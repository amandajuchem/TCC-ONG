import { FormGroup, FormControl, FormArray } from '@angular/forms';

export class FormUtils {

    static getErrorMessage(form: FormGroup | FormArray, controlName: string): string {
        for (const name in form.controls) {
            const control = form.get(name);

            if (name === controlName) {
                if (control instanceof FormControl) {
                    if (control.hasError('required')) {
                        return 'Campo obrigatório!';
                    }

                    if (control.hasError('maxlength')) {
                        const maxLength = control.getError('maxlength').requiredLength;
                        return `Tamanho máximo excedido! Limite: ${maxLength}`;
                    }

                    if (control.hasError('mask')) {
                        return 'Campo inválido!';
                    }
                }
            }

            if (control instanceof FormGroup || control instanceof FormArray) {
                const errorMessage = this.getErrorMessage(control, controlName);
                if (errorMessage) {
                    return errorMessage;
                }
            }
        }

        return 'Error';
    }

    static hasError(form: FormGroup | FormArray, controlName: string): boolean {
        for (const name in form.controls) {
            const control = form.get(name);

            if (name === controlName) {
                if (control instanceof FormControl) {
                    return (
                        control.hasError('required') ||
                        control.hasError('maxlength') ||
                        control.hasError('mask')
                    );
                }
            }

            if (control instanceof FormGroup || control instanceof FormArray) {
                if (this.hasError(control, controlName)) {
                    return true;
                }
            }
        }

        return false;
    }
}