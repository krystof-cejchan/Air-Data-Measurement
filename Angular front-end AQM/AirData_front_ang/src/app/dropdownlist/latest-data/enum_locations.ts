//unused

enum LocationsEnum {
    PdF, PrF, PF, LF, FZV, FTK, FF, CMTF, OTHER
}

function getBcgImageUrl(location: LocationsEnum): string {
    var returnValue = "";
    switch (location) {
        case LocationsEnum.PdF:
            returnValue = "asse"
            break;
        case LocationsEnum.PrF:

            break;
        case LocationsEnum.PF:

            break;
        case LocationsEnum.LF:

            break;
        case LocationsEnum.FZV:

            break;
        case LocationsEnum.FTK:

            break;
        case LocationsEnum.FF:

            break;
        case LocationsEnum.CMTF:

            break;
        default:
            break;
    }
    return returnValue;
}

