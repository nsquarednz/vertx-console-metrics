export default function (value) {
    const
        days = Math.floor(value / 86400),
        hours = Math.floor((value - (days * 86400)) / 3600),
        minutes = Math.floor((value - (days * 86400) - (hours * 3600)) / 60),
        seconds = Math.round(value - (days * 86400) - (hours * 3600) - (minutes * 60));

    if (days > 0) {
        return `${days}d ${hours}h ${minutes}m`
    } else if (hours > 0) {
        return `${hours}h ${minutes}m`
    } else if (minutes > 0) {
        return `${minutes}m ${seconds}s`
    } else {
        return `${seconds}s`;
    }

    return resultStr;
};