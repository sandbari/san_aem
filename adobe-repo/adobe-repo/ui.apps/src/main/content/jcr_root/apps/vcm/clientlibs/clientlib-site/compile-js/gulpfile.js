var gulp,
  srcFolder,
  jsBaseFolder,
  babel,
  es2015,
  rename;

gulp = require('gulp');
babel = require('gulp-babel');
es2015 = require('babel-preset-es2015');
rename = require('gulp-rename');

srcFolder = '../../';
jsBaseFolder = srcFolder + 'foot/js/';

// THIS DEFINES THE GULP TASK
gulp.task('compile-js', function() {
  return gulp.src([jsBaseFolder + '**/*.js', '!' + jsBaseFolder + '_compiled/**', '!' + jsBaseFolder + 'vendor/**'])
    .pipe(babel({
      "presets": [es2015]
    }))
    .pipe(rename({dirname: ''}))
    .pipe(gulp.dest(jsBaseFolder + '_compiled'))
});

// THIS RUNS THE TASK FIRED BY THE MAVEN BUILD
gulp.task('build', ['compile-js']);