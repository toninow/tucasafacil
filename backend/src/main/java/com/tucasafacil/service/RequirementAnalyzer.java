package com.tucasafacil.service;

import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.PropertyRequirement;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
public class RequirementAnalyzer {

    private static final Pattern PERMANENT_CONTRACT = Pattern.compile("contrato indefinido|contrato fijo", Pattern.CASE_INSENSITIVE);
    private static final Pattern PAYSLIPS = Pattern.compile("(\\d+) últimas? nóminas?", Pattern.CASE_INSENSITIVE);
    private static final Pattern DEPOSIT = Pattern.compile("(\\d+) meses? de fianza", Pattern.CASE_INSENSITIVE);
    private static final Pattern ADVANCE = Pattern.compile("(\\d+) meses? por adelantado", Pattern.CASE_INSENSITIVE);
    private static final Pattern GUARANTOR = Pattern.compile("aval|garantía", Pattern.CASE_INSENSITIVE);
    private static final Pattern INSURANCE = Pattern.compile("seguro de impago", Pattern.CASE_INSENSITIVE);
    private static final Pattern PETS = Pattern.compile("no mascotas|mascotas no", Pattern.CASE_INSENSITIVE);
    private static final Pattern PROFILE = Pattern.compile("estudiantes?|parejas?|familias?|trabajadores?", Pattern.CASE_INSENSITIVE);

    public void analyze(Property property) {
        String text = property.getDescription();
        if (text == null) return;

        PropertyRequirement req = new PropertyRequirement();
        req.setProperty(property);

        req.setRequiresPermanentContract(PERMANENT_CONTRACT.matcher(text).find());

        var payslipsMatcher = PAYSLIPS.matcher(text);
        if (payslipsMatcher.find()) {
            req.setRequiredPayslips(Integer.parseInt(payslipsMatcher.group(1)));
        }

        var depositMatcher = DEPOSIT.matcher(text);
        if (depositMatcher.find()) {
            req.setDepositMonths(Integer.parseInt(depositMatcher.group(1)));
        }

        var advanceMatcher = ADVANCE.matcher(text);
        if (advanceMatcher.find()) {
            req.setAdvanceMonths(Integer.parseInt(advanceMatcher.group(1)));
        }

        req.setRequiresGuarantor(GUARANTOR.matcher(text).find());
        req.setRequiresRentDefaultInsurance(INSURANCE.matcher(text).find());
        req.setPetsAllowed(!PETS.matcher(text).find());

        var profileMatcher = PROFILE.matcher(text);
        if (profileMatcher.find()) {
            req.setPreferredProfile(profileMatcher.group());
        }

        // Estimar ingreso mínimo basado en precio (mock)
        if (property.getPrice() != null) {
            req.setEstimatedMinimumIncome(property.getPrice().multiply(BigDecimal.valueOf(3)));
        }

        property.getRequirements().add(req);
    }
}