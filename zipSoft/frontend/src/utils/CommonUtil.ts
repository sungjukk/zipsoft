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
            alert(`${element.getAttribute('title')}을 입력해주세요.`);
            element.focus();
            isValid = false;
            break;
        }
    }


    return isValid;

}