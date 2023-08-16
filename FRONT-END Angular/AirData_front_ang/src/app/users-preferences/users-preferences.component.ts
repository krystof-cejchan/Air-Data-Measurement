import { Component, OnInit } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { PrefOpt } from "./PrefOpt";
import { FormGroup } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { popUpSnackBar } from "../utilities/utils";
@Component({
  selector: 'app-users-preferences',
  templateUrl: './users-preferences.component.html',
  styleUrls: ['./users-preferences.component.scss']
})
export class UsersPreferencesComponent implements OnInit, IComponent {
  prefMap = new Map<PrefOpt | string, boolean>;


  constructor(private cookieService: CookieService, private snackBar: MatSnackBar) { }

  getTitle(): string {
    return "Uživatelské preference"
  }

  ngOnInit(): void {
    for (let key in PrefOpt) {

      const cookieValue = this.cookieService.get(key)
      if (!cookieValue || cookieValue == "false") {
        this.savePrefToMap(key, false, !!cookieValue)
      }
      else {
        this.savePrefToMap(key, true, true)
      }
    }
  }

  isChecked(index: number): boolean {
    const prefValue = this.cookieService.get(this.getPrefEnumByIndex(index));
    return (prefValue != null && prefValue == "true")
  }
  public async savePreferences(formGroup: FormGroup, showSnackBar: boolean) {
    Object.keys(PrefOpt).forEach(key => {
      const prefValue = formGroup.value[key];
      this.savePrefToMap(key, prefValue != null && prefValue == true, showSnackBar)
    })
    if (showSnackBar)
      popUpSnackBar(this.snackBar, "Preference byly úspěšně uloženy.", 2, false);
  }
  getPrefEnumByIndex(index: number): string {
    return Object.keys(PrefOpt)[index];
  }
  private savePrefToMap(pref: PrefOpt | string, prefValue: boolean, saveCookies: boolean = true) {
    if (!pref) return;
    if (saveCookies)
      this.cookieService.set(pref.toString(), String(prefValue));

    this.prefMap.set(pref, prefValue);
  }

  /**
   * converts string to PrefOpt enum value (not key!!!)
   * @param p string from PrefOpt
   * @returns enum as PrefOpt if exists
   */
  stringToEnumDescription(p: string): PrefOpt {
    return PrefOpt[p as keyof typeof PrefOpt]
  }
}