class TddBmiCalc_And_BubbleSort:

    @staticmethod
    def TypeOfBmi(bmi):
        if bmi < 18.5:
            return 'Underweight'
        if bmi >= 18.5:
            if bmi <= 25:
                return 'Healthy'
            if bmi > 25:
                return 'Overweight'