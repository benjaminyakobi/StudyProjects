import unittest
from TddBmiCalc_And_BubbleSort import TddBmiCalc_And_BubbleSort

class TddBmiCalcTest_And_BubbleSortTest(unittest.TestCase):
    def test_TypeOfBmi(self):
        # stub = bmi
        stub1 = 25
        stub2 = 25.1
        stub3 = 30
        stub4 = 17

        # assume
        expected1 = 'Healthy'
        expected2 = 'Overweight'
        expected3 = 'Overweight'
        expected4 = 'Underweight'

        # action
        result1 = TddBmiCalc_And_BubbleSort.TypeOfBmi(stub1)
        result2 = TddBmiCalc_And_BubbleSort.TypeOfBmi(stub2)
        result3 = TddBmiCalc_And_BubbleSort.TypeOfBmi(stub3)
        result4 = TddBmiCalc_And_BubbleSort.TypeOfBmi(stub4)

        # expect/assert
        self.assertEqual(result1, expected1)
        self.assertEqual(result2, expected2)
        self.assertEqual(result3, expected3)
        self.assertEqual(result4, expected4)

if __name__ == '__main__':
    unittest.main()