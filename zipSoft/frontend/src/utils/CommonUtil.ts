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