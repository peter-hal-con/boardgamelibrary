import fetch from 'node-fetch'

export function bggId(itemElement) {
    return itemElement.getAttribute('id');
}

export function primaryName(itemElement) {
    var retval = null;
    Array.prototype.slice.call(itemElement.getElementsByTagName("name")).forEach(nameElement => {
        if (retval === null || nameElement.getAttribute('type') === 'primary') {
            retval = nameElement.getAttribute('value');
        }
    })
    return retval;
}

export function yearPublished(itemElement) {
    const yearpublishedElements = itemElement.getElementsByTagName("yearpublished");
    return (yearpublishedElements.length > 0) ? yearpublishedElements[0].getAttribute("value") : null;
}

export function displayName(itemElement) {
    const _primaryName = primaryName(itemElement);
    const _yearPublished = yearPublished(itemElement);
    return _yearPublished === null ? _primaryName : `${_primaryName} (${_yearPublished})`
}

export function createItemWithPrimaryName(primaryName) {
    return new window.DOMParser().parseFromString("<item id='none' type='boardgame'><name type='primary' value='" + primaryName + "'/></item>", 'text/xml').documentElement
}

export async function searchBgg(searchInput) {
    return await fetch('https://boardgamegeek.com/xmlapi2/search?type=boardgame,boardgameaccessory,boardgameexpansion&query=' + encodeURIComponent(searchInput.replace(/\s/g, '+')) + '')
        .then(response => response.text())
        .then(text => new window.DOMParser().parseFromString(text, 'text/xml'))
        .then(xml => (Array.prototype.slice.call(xml.documentElement.getElementsByTagName('item'))))
}
