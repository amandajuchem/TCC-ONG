import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TutoresExcluirComponent } from './tutores-excluir.component';

describe('TutoresExcluirComponent', () => {
  let component: TutoresExcluirComponent;
  let fixture: ComponentFixture<TutoresExcluirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TutoresExcluirComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TutoresExcluirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
