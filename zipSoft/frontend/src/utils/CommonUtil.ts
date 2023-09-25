import { globals } from '@/main';

export const phoneFormat = (hpNo : string) => {
    return hpNo ? hpNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3") : hpNo;
};

export const numberExp = (number : string) => {

    if (!number) return false;

    const exp = /^[0-9]/g;
    return exp.test(number);
};

export const emailExp = (email : string) => {

    if (!email) return false;

    const exp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    return exp.test(email);
}

export const dateFormat = (date:string, pattern : string) => {
    const pat = pattern.replace('yyyy','$1').replace('MM','$2').replace('dd','$3').replace('HH','$4').replace('mm','$5').replace('ss','$6');
    return date.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, pat);
}

export const timeForToday = (value : string) => {

    const exp = /\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[0-9]|2[0-4])(0[1-9]|[1-5][0-9])(0[1-9]|[1-5][0-9])/;

    if (!exp.test(value)) return value;

    const today = Math.floor(new Date().getTime() - new Date(dateFormat(value,'yyyy-MM-dd HH:mm:ss')).getTime());
    const betweenTime = Math.floor(today / 1000 / 60);
    if (betweenTime < 1) return '방금전';
    if (betweenTime < 60) return `약 ${betweenTime}분전`;

    const betweenHour = Math.floor(betweenTime / 60);
    if (betweenHour < 24) return `약 ${betweenHour}시간전`;

    const betweenDay = Math.floor(betweenHour / 24);
    if (betweenDay < 365) return `약 ${betweenDay}일전`;

    return value;

}

export const convert12H = (value : string) => {
    const today = new Date();
    const valueDate = new Date(dateFormat(value, 'yyyy-MM-dd HH:mm:ss'));
    
    if (today.getFullYear() - valueDate.getFullYear() >= 1) return dateFormat(value, 'yyyy. MM. dd');

    const result = Math.floor(today.getTime() - valueDate.getTime());
    const betweenDay = Math.floor(result / 1000 / 60 / 60 / 24);

    if (betweenDay > 1) {
        return dateFormat(value, 'MM월 dd일');
    } else if (betweenDay == 1) {
        return '어제';
    } else {
        let txt = '';
        let hour;
        if (valueDate.getHours() >= 12) {
            txt = '오후';
            hour = valueDate.getHours() - 12;
        } else {
            txt = '오전';
            hour = valueDate.getHours();
        }
        return `${txt} ${hour >= 10 ? hour : '0' + hour}:${valueDate.getMinutes() >= 10 ? valueDate.getMinutes() : '0' + valueDate.getMinutes()}`;
    }

}

export const validationCheck = (formId : string) => {

    const validArr:NodeListOf<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement> = document.querySelectorAll(`#${formId} input, #${formId} select, #${formId} textarea`);
    let isValid:boolean = true;

    for (let idx = 0; idx < validArr.length; idx++) {
        const element = validArr[idx];
        const isReq = element.getAttribute('data-require');
        console.log(isReq);
        if (isReq == null || typeof(isReq) == 'undefined') {
            continue;
        }

        if (element.value == '') {
            globals.$alert(`${element.getAttribute('title')}을 입력해주세요.`, null, element);
            isValid = false;
            break;
        }
    }


    return isValid;

}