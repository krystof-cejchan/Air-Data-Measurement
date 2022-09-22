function dateAndTimeFormat() {
    var elements = document.querySelectorAll("#airdata_receivedDataDateTime");
    console.log(elements);
    elements.forEach(element => {
        console.log(element);
        if (!(element.innerText === null || element.innerText.lenght === 0)) {
            var elementText = element.innerText;
            try {
                element.innerText = formatDateAndTime(elementText);
            }
            catch (error) {
                element.innerText = elementText;
            }
        }
    });
}

function formatDateAndTime(unformattedDate) {
    // example:  2022-09-18T16:00:15 ::: js can handle java date format 
    const date = new Date(unformattedDate);

    var options = { dateStyle: 'short', timeStyle: 'short' };

    return new Intl.DateTimeFormat('cs-CZ', options).format(date);
}