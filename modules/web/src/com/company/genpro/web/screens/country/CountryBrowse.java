package com.company.genpro.web.screens.country;

import com.haulmont.cuba.gui.screen.*;
import com.company.genpro.entity.Country;

@UiController("Country.browse")
@UiDescriptor("country-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class CountryBrowse extends MasterDetailScreen<Country> {
}