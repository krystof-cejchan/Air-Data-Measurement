import { Component, OnInit, OnDestroy } from "@angular/core";
import { ForecastData } from "./forecastobject";
import { ForecastService } from "./forecast.service";
import moment from "moment";
import { round, uniqByFilter } from "../utilities/utils";
import { ActivatedRoute, NavigationEnd, Router } from "@angular/router";
import { MatSnackBar } from "@angular/material/snack-bar";
import { openSnackBar } from "../errors/custom in-page errors/snack-bar/server_error/custom-error-snackbar";
import { filter } from "rxjs";
import { SubSink } from "subsink";


interface sliderParams {
  disabled: boolean,
  max: number,
  min: number,
  showTicks: boolean,
  step: number,
  thumbLabel: boolean,
  value: number
}

@Component({
  selector: 'app-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.scss']
})
export class ForecastComponent implements OnInit, IComponent, OnDestroy {

  private subs = new SubSink()
  colorBasedOnWeather: string = "transparent";
  times: string[] = ["AM_3", "AM_6", "AM_9", "AM_12", "PM_3", "PM_6", "PM_9", "PM_12"];
  weatherforecastDays: string[] = ['TODAY', 'TOMORROW', 'AFTER_TOMORROW'];
  weatherforecastDays_cs: string[] = ['Dnes', 'Zítra', 'Pozítří'];
  private momentFormat: string = 'D_M';
  filteredForecast: ForecastData[] = [];

  availableDates: string[] = [moment().add(0, 'days').format(this.momentFormat),
  moment().add(1, 'days').format(this.momentFormat),
  moment().add(2, 'days').format(this.momentFormat)];

  days: string[] = [];
  forcastData: ForecastData[] = [];
  formattedTime: string = moment().format('D.M.y h:m:s');
  day_param: string = "";
  day_param_formatted: string = "";
  time_param: string = "";
  durationInSeconds = 10;
  private currentHour = moment().hour();


  slider_params: sliderParams = {
    disabled: false,
    max: 21,
    min: 0,
    showTicks: true,
    step: 3,
    thumbLabel: true,
    value: 0
  };

  constructor(private service: ForecastService, private route: ActivatedRoute, private router: Router,
    private snackBar: MatSnackBar) {
  }
  ngOnDestroy(): void {
    this.subs.unsubscribe()
  }

  getTitle(): string {
    return "Předpověď počasí";
  }

  ngOnInit(): void {
    this.day_param = this.route.snapshot.params['day'];
    this.time_param = this.route.snapshot.params['time'];
    //console.log(this.day_param + "\t" + this.time_param);

    const indexofDate = this.availableDates.indexOf(this.route.snapshot.params['day']);

    const isTimeOK = (this.route.snapshot.params['time'] != undefined && this.times.includes(this.time_param))
    const isDateOK = (this.route.snapshot.params['day'] != undefined && indexofDate >= 0)

    const closestTime = this.getClosestTime();
    const date = this.availableDates[0];

    if (!(isDateOK && isTimeOK))
      this.router.navigate([`predpoved/${date}/${closestTime}`]);
    else if (!isDateOK)
      this.router.navigate([`predpoved/${date}/${this.time_param}`]);
    else if (!isTimeOK)
      this.router.navigate([`predpoved/${this.day_param}/${closestTime}`]);

    this.slider_params.value = this.getClosestNumberTime();
    this.day_param_formatted = indexofDate === 0 ? 'TODAY' : (indexofDate === 1 ? 'TOMORROW' : 'AFTER_TOMORROW')

    this.subs.add(this.service.getAllForecastData().subscribe({
      next: async (response) => {
        this.forcastData = response;

        const msToWait = 400, msMaxToWait = 5000;
        var counter = 0;
        while (this.forcastData.length === 0 && counter < (msMaxToWait / msToWait)) {
          await new Promise(f => setTimeout(f, msToWait));
          counter++;
        }

        this.times = uniqByFilter<string>(this.forcastData.map(it => it.time));
        this.updateFilteredData();
      },
      error: () => openSnackBar(this.snackBar),
      complete: () => {
        this.subs.add(this.router.events
          .pipe(filter((event): event is NavigationEnd => event instanceof NavigationEnd))
          .subscribe(() => {
            this.updateFilteredData();
          }))
      }
    }))

  }

  private getClosestTime(hour: number = this.currentHour): string {
    const times: number[] = [0, 3, 6, 9, 12, 15, 18, 21]
    let closest = times.filter(time => time >= hour).reduce((prev, curr) => Math.abs(curr - hour) < Math.abs(prev - hour) ? curr : prev, 0);
    if (closest === 0) closest = 24;
    const morning = closest <= 12;
    let preffix = morning ? "AM_" : "PM_"
    return preffix + (morning ? closest : closest - 12).toString();
  }

  getDayIndex(): number {
    return this.availableDates.indexOf(this.route.snapshot.params['day']);
  }
  getClosestNumberTime(hour: string = this.route.snapshot.params['time']): number {
    if (!hour)
      return 0;
    const morning = hour.includes('AM')
    const number = Number(hour.substring(hour.indexOf('_') + 1))
    if (!morning && number === 12)
      return 0;
    return morning ? number : number + 12
  }

  getDateFromDay(day: "Dnes" | "Zítra" | "Pozítří" | string): string {
    return moment().add(day === 'Dnes' ? 0 : (day == 'Zítra' ? 1 : 2), 'days').format('D.M.')
  }

  getForeCastForADay(day: string): ForecastData[] {
    return this.forcastData.filter(it => it.day === day)
  }

  updateFilteredData(): void {
    const indexofDate = this.availableDates.indexOf(this.route.snapshot.params['day']);
    this.day_param_formatted = indexofDate === 0 ? 'TODAY' : (indexofDate === 1 ? 'TOMORROW' : 'AFTER_TOMORROW')
    const filteredData = this.forcastData.filter(data => data.time == this.route.snapshot.params['time'] && data.day == this.day_param_formatted);
    this.filteredForecast = filteredData
    //return filteredData;
  }

  getFilteredForecast(): ForecastData[] {
    return this.filteredForecast
  }

  setTime(time: string): void {
    this.router.navigate([`predpoved/${this.route.snapshot.params['day']}/${time}`]);
  }
  setDate(index: number): void {
    this.router.navigate([`predpoved/${this.availableDates[index]}/${this.route.snapshot.params['time']}`]);
  }
  /**
   * formats output for a slider; takes its value and adds :00
   * @param value slider current value
   * @param zeros 1→add :00 else→. hodina
   * @returns formatted output
   */
  formatOutput(value: number, zeros: boolean = true): string {
    if (!zeros)
      return `${value}. hodina`;
    else return `${value}:00`;
  }

  public dragOver() {
    this.setTime(this.getClosestTime(this.slider_params.value))
  }
  /**
   * takes english cardinal direction and rewrites them in Czech;
   * works on a easy algorithm in which basic directions like North, South, East and West are the only directions the subdirections are made of,
   *  for instance: NE = North and East.
   * Letters (N, S, E, W) are replace by its czech equalients (S, J, V, Z)
   * @param dir shortcuts for the directions such as N, SSE, NNW...
   *  West-northwest (WNW),
   *  North-northwest (NNW),
   *  North-northeast (NNE), 
   *  East-northeast (ENE),   
   *  East-southeast (ESE),     
   *  South-southeast (SSE),
   *  South-southwest (SSW),     
   *  West-southwest (WSW) + North, South, West and East
   * @returns translated english cardinal directions; for example: ESE = VJV → East(E)=Východ(V), South(S)=Jih(J).
   */
  windDirTranslate(dir: string): string {
    var windDirCzech = "";
    for (let i = 0; i < dir.length; i++) {
      switch (dir[i]) {
        case "N":
          windDirCzech += 'S'
          break;
        case "E":
          windDirCzech += 'V'
          break;
        case "S":
          windDirCzech += 'J'
          break;
        case "W":
          windDirCzech += 'Z'
          break;
        default: break;
      }
    }
    return windDirCzech;
  }
  /**
   * calculates average temperature of a specific day
   * @param index 0=today, 1=tomorrow, 2= the day after tomorrow
   * @param night true = only night times, false = only day times, null = all times
   * @returns average of temperature in celsius
   */
  calcDayAvg(index: number, night: boolean | null = null): number {
    const nightTimes = ["PM_12", "PM_9", "AM_3", "AM_6"];
    const dayTimes = this.times.filter(time => !nightTimes.includes(time))

    const filteredTemerature = this.forcastData
      .filter(data => data.day === this.weatherforecastDays[index] && (night ? nightTimes.includes(data.time) : (!night ? dayTimes.includes(data.time) : true)))
      .map(filteredData => filteredData.temperatureC)

    return round(filteredTemerature.reduce((acc, val) => acc + val, 0) / filteredTemerature.length, 1);
  }

  /**
   * WeatherCode	Condition	DayIcon	NightIcon
395	Moderate or heavy snow in area with thunder
392	Patchy light snow in area with thunder	
389	Moderate or heavy rain in area with thunder
386	Patchy light rain in area with thunder	
377	Moderate or heavy showers of ice pellets	
374	Light showers of ice pellets	
371	Moderate or heavy snow showers
368	Light snow showers
365	Moderate or heavy sleet showers
362	Light sleet showers	
359	Torrential rain shower	
356	Moderate or heavy rain shower
353	Light rain shower	
350	Ice pellets	
338	Heavy snow	
335	Patchy heavy snow	
332	Moderate snow
329	Patchy moderate snow	
326	Light snow
323	Patchy light snow
320	Moderate or heavy sleet	
317	Light sleet
314	Moderate or Heavy freezing rain
311	Light freezing rain
308	Heavy rain
305	Heavy rain at times
302	Moderate rain
299	Moderate rain at times
296	Light rain	
293	Patchy light rain
284	Heavy freezing drizzle
281	Freezing drizzle
266	Light drizzle
263	Patchy light drizzle
260	Freezing fog
248	Fog
230	Blizzard	
227	Blowing snow
200	Thundery outbreaks in nearby
185	Patchy freezing drizzle nearby
182	Patchy sleet nearby
179	Patchy snow nearby
176	Patchy rain nearby
143	Mist
122	Overcast	
119	Cloudy
116	Partly Cloudy
113	Clear/Sunny
   * @param code weather code
   * @returns weather condition based on the weather code
   */
  weatherCodeToWeatherDesc(code: number): string {
    switch (String(code)) {
      case "113":
        return "Slunečno";
      case "116":
        return "Částečně zataženo";
      case "119":
        return "Zataženo";
      case "122":
        return "Zamračeno";
      case "143":
        return "Opar/Mlha";
      case "176":
        return "Nepravidelný déšť";
      case "179":
        return "Nepravidelné sněžení";
      case "182":
        return "Nepravidelná plískanice";
      case "185":
        return "Nepravidelné mrholení";
      case "200":
        return "Náhlá bouřka";
      case "227":
        return "Vítr se sněhem";
      case "230":
        return "Blizard";
      case "248":
        return "Mlha";
      case "260":
        return "Mrznoucí mlha";
      case "263":
        return "Nepravidelné slabé mrholení";
      case "266":
        return "Slabé mrholení";
      case "281":
        return "Mrznoucí mrholení";
      case "284":
        return "Silné mrznoucí mrholení";
      case "293":
        return "Občasný slabý déšť";
      case "296":
        return "Slabý déšť";
      case "299":
        return "Občasný mírný déšť";
      case "302":
        return "Mírný déšť";
      case "305":
        return "Občasný silný déšť";
      case "308":
        return "Silný déšť";
      case "311":
        return "Slabý mrznoucí déšť";
      case "314":
        return "Mírný nebo silná mrznoucí déšť";
      case "317":
        return "Mírná plískanice";
      case "320":
        return "Mírná nebo silná plískanice";
      case "323":
        return "Občasné slabé sněžení";
      case "326":
        return "Slabé sněžení";
      case "329":
        return "Občasné mírné sněžení";
      case "332":
        return "Mírné sněžení";
      case "335":
        return "Nepravidelné silné sněžení";
      case "338":
        return "Silné sněžení";
      case "350":
        return "Ledové pelety";
      case "353":
        return "Lehká dešťová sprcha";
      case "356":
        return "Mírná nebo silná dešťová sprcha";
      case "359":
        return "Přívalová dešťová sprcha";
      case "362":
        return "Slabé přeháňky plískanice";
      case "365":
        return "Mírné nebo silné plískanicová sprcha";
      case "368":
        return "Slabá sněžná sprcha";
      case "371":
        return "Mírná nebo silná sněžná sprcha";
      case "374":
        return "Slabý déšť se sněhem";
      case "377":
        return "Slabý déšť se sněhem";
      case "386":
        return "Slabé přeháňky ledových pelet";
      case "389":
        return "Nepravidelný slabý déšť s bouřkou";
      case "392":
        return "Nepravidelné slabé sněžení s bouřkou";
      case "395":
        return "Mírné nebo silné sněžení s bouřkou";
      default:
        return "Chyba";
    }

  }
  translateDay(day: string): string {
    return this.weatherforecastDays_cs[this.weatherforecastDays.indexOf(day)] || "";
  }
}