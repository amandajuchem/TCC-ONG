export class DateUtils {

    /**
     * 
     * @param date 
     * @returns 
     */
    static getDateWithTimeZone(date: any) {
        return new Date((new Date(date).getTime() - (new Date(date).getTimezoneOffset() * 60000)));
    }
}