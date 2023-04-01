export class DateUtils {

    static offsetBrasilia: number = -3;

    /**
     * 
     * @param date 
     * @param hours 
     */
    static addHours(date: Date, hours: number) {
        date = new Date(date);
        date.setHours(date.getHours() + hours);
        return date;
    }

    /**
     * 
     * @param date 
     * @returns 
     */
    static getDateWithTimeZone(date: any) {
        date = new Date(date);
        return new Date(date.getTime() - (date.getTimezoneOffset() * 60000));
    }

    /**
     * 
     * @param date 
     * @returns 
     */
    static getDateWithoutTimeZone(date: any) {
        date = new Date(date);
        return new Date(date.getTime() + (date.getTimezoneOffset() * 60000));
    }

    /**
     * 
     * @param date 
     * @returns 
     */
    static getDateTimeWithoutSecondsAndMilliseconds(date: any): Date {

        date = new Date(date);

        date.setSeconds(0);
        date.setMilliseconds(0);

        return date;
    }
}