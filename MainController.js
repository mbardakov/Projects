app.controller('MainController', ['$scope',
    function ($scope) {
        $scope.title = 'Arenafy';
        $scope.left = 0;
        $scope.right = 0;
        $scope.placeholder = 'Enter at least two items...';
        $scope.sampleContestants = [
            { name: 'Fish & Chips',
                sum: 1200,
                wins: 0,
                losses: 0
            },
            { name: 'Ceasar Salad',
                sum: 1200,
                wins: 0,
                losses: 0
            },
            { name: 'Grilled Cheese',
                sum: 1200,
                wins: 0,
                losses: 0
            },
            { name: 'Nachos',
                sum: 1200,
                wins: 0,
                losses: 0
            },
            { name: 'Pulled Pork Panini',
                sum: 1200,
                wins: 0,
                losses: 0
            },
            { name: 'Duck Tacos',
                sum: 1200,
                wins: 0,
                losses: 0
            }
        ];
        $scope.contestants = [
            {
                name: $scope.placeholder
            }
        ];
        $scope.colours = [
            '#e1f7d5',
            '#ffbdbd',
            '#c9c9ff',
            '#f1cbff'
        ];
        $scope.getPerformanceRating = function ($index) {
            return ($scope.contestants[$index].sum + (400 *
                ($scope.contestants[$index].wins - $scope.contestants[$index].losses))) /
                (1 + $scope.contestants[$index].wins + $scope.contestants[$index].losses);
        };
        $scope.update = function ($winner, $loser) {
            var oldWinnerRating = $scope.getPerformanceRating($winner);
            var oldLoserRating = $scope.getPerformanceRating($loser);
            $scope.contestants[$winner].sum += oldLoserRating;
            $scope.contestants[$loser].sum += oldWinnerRating;
            $scope.contestants[$winner].wins += 1;
            $scope.contestants[$loser].losses += 1;
            $scope.left = Math.floor(Math.random() * $scope.contestants.length);
            $scope.right = Math.floor(($scope.left + (1 + Math.random() * ($scope.contestants.length - 1))) % $scope.contestants.length);
        };
        $scope.addNew = function () {
            var newname = document.getElementById('inputbox').innerText;
            $scope.contestants.push({
                name: newname,
                sum: 1200,
                wins: 0,
                losses: 0
            });
            if ($scope.contestants[0].name === $scope.placeholder &&
                $scope.contestants.length > 2) {
                $scope.contestants.splice(0, 1);
                $scope.left = 0;
                $scope.right = 1;
            }
            document.getElementById('inputbox').innerHTML = "Add new item";
        };
        // add new item with "enter" as well as on click
        document.getElementById("inputbox")
            .addEventListener("keyup", function (event) {
            if (event.keyCode == 13) {
                document.getElementById('plusdiv').click();
            }
        });
        $scope.range = function (n) {
            return new Array(n);
        };
    }
]);
