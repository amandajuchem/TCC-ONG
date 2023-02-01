import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalAdocoesCadastroComponent } from './animal-adocoes-cadastro.component';

describe('AnimalAdocoesCadastroComponent', () => {
  let component: AnimalAdocoesCadastroComponent;
  let fixture: ComponentFixture<AnimalAdocoesCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimalAdocoesCadastroComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimalAdocoesCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
